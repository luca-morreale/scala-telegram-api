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

import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.conn.ssl.NoopHostnameVerifier

import java.util.concurrent.TimeUnit

import org.telegram.bot.util.BotLogger

/**
 * Base class which provides basic functionalities
 * usable for both send and receive methods.
 */

abstract class BaseMethod(token: String, timeout: Int=BaseMethod.defaultTimeout) {

    private val log = BotLogger.getLogger(classOf[BaseMethod].getName)

    private val closeableHTTPClient = HttpClientBuilder.create.setSSLHostnameVerifier(new NoopHostnameVerifier)
                                                                .setConnectionTimeToLive(timeout, TimeUnit.SECONDS).build

    /**
     * Returns the URL to reach the service exploited by the bot
     */
    def url(): String = "https://api.telegram.org/bot"

    /**
     * Returns the unique token of the bot
     */
    def token(): String = token

    protected def logger(): BotLogger = log

    protected def httpClient(): CloseableHttpClient = closeableHTTPClient

}

object BaseMethod {

    val defaultTimeout = 20
}
