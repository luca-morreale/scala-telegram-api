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
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.client.methods.HttpPost
import org.json4s._
import org.telegram.bot.api.{APIClass, formats}
import org.telegram.bot.methods.AnswerHandler
import org.telegram.bot.methods.BaseMethod
import org.json4s.jackson.JsonMethods.parse


/**
 * Basic specialization of BaseMethod intended for receiver methods.
 */

abstract class DataReceiver(token: String, timeout: Int=BaseMethod.defaultTimeout) extends BaseMethod(token, timeout) {

    protected def handleAnswer[API >: Null: Manifest](httpClient: CloseableHttpClient,
                                                            httpPost: HttpPost): Option[API] = {
        handleAnswer[Option[API]](httpClient, httpPost, defaultConverter[API] _ )
    }

    protected def handleAnswer[API >: Null](httpClient: CloseableHttpClient,
                                                                   httpPost: HttpPost,
                                                                   converter: (JValue => API)): API = {
        try {
            val response = executeCall(httpPost)
            val json = parseJson(response)
            converter(json)
        } catch {
            case cp: ClientProtocolException =>
                logger debug cp
                null
        }
    }

    private def defaultConverter[API: Manifest](json: JValue): Option[API] = json.extractOpt[API]

    protected def executeCall(httpPost: HttpPost): String = {
        httpClient.execute(httpPost, new AnswerHandler)
    }

    protected def parseJson(response: String): JValue = {
        parse(response) \ APIClass.innerEntry
    }
}
