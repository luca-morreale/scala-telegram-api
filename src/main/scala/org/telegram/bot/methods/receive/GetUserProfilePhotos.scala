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

package org.telegram.bot.methods.receive

import org.apache.http.client.ClientProtocolException
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.conn.ssl.NoopHostnameVerifier

import org.telegram.bot.api.formats
import org.telegram.bot.util.BotLogger
import org.telegram.bot.methods.BaseMethod
import org.telegram.bot.TelegramInformation
import org.telegram.bot.api.UserProfilePhotos
import org.telegram.bot.methods.AnswerHandler
import org.telegram.bot.methods.buildValuePairs
import org.telegram.bot.methods.generateHttpPost

import java.util.concurrent.TimeUnit

import scala.collection.immutable.HashMap

import org.json4s.jackson.JsonMethods.parse
import org.json4s.string2JsonInput
import org.json4s.jvalue2monadic
import org.json4s.JValue

/**
 *
 */

class GetUserProfilePhotos(token: String, timeout: Int) extends BaseMethod(token) with TelegramInformation {

    private val log = BotLogger.getLogger(classOf[GetUserProfilePhotos].getName)

    val path = "getuserprofilephotos"

    private val httpClient = HttpClientBuilder.create
                                .setSSLHostnameVerifier(new NoopHostnameVerifier)
                                .setConnectionTimeToLive(timeout, TimeUnit.SECONDS).build

    private val url = telegramPath + token + "/" + path

    def request(user_id: Int, offset: Int, limit: Int): Option[UserProfilePhotos] = {

        val pairs = buildValuePairs(HashMap("user_id" -> user_id.toString,
                            "offset" -> offset.toString, "limit" -> limit.toString))
        val httpPost = generateHttpPost(url)

        debug(httpPost, pairs)

        try {
            val response = httpClient.execute(httpPost, new AnswerHandler)
            val json = parse(response) \ "result"
            json.extractOpt[UserProfilePhotos]
        } catch {
            case cp: ClientProtocolException => None
        }
    }

}
