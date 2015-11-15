/*
 *  Copyright (C) 2015  Morreale Luca
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.telegram.bot.methods

import org.apache.http.client.ClientProtocolException
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.conn.ssl.NoopHostnameVerifier
import org.apache.http.client.methods.HttpPost
import org.apache.http.NameValuePair

import java.util.concurrent.TimeUnit

import org.json4s.jackson.JsonMethods.parse

import org.telegram.bot.api.formats
import org.telegram.bot.util.BotLogger

/**
 *
 */

abstract class BaseMethod(token: String, timeout: Int=BaseMethod.defaultTimeout) extends Method {

    private val log = BotLogger.getLogger(classOf[BaseMethod].getName)

    private val closeableHTTPClient = HttpClientBuilder.create.setSSLHostnameVerifier(new NoopHostnameVerifier)
                                                                .setConnectionTimeToLive(timeout, TimeUnit.SECONDS).build

    def url(): String = "https://api.telegram.org/bot"

    def token(): String = token

    protected def logger(): BotLogger = log

    protected def httpClient(): CloseableHttpClient = closeableHTTPClient

    protected def handleAnswer[API: Manifest](httpClient: CloseableHttpClient, httpPost: HttpPost): Option[API] = {
        try {
            val response = httpClient.execute(httpPost, new AnswerHandler)
            val json = parse(response) \ "result"
            json.extractOpt[API]
        } catch {
            case cp: ClientProtocolException =>
                log.debug(cp)
                None
        }
    }
}


private object BaseMethod {

    val defaultTimeout = 20
}
