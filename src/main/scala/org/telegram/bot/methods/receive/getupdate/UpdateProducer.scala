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

package org.telegram.bot.methods.receive.getupdate

import java.util.concurrent.TimeUnit

import org.apache.http.client.ClientProtocolException
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.conn.ssl.NoopHostnameVerifier
import org.apache.http.client.methods.HttpPost
import org.apache.http.NameValuePair

import org.telegram.bot.api.Update
import org.telegram.bot.util.BotLogger
import org.telegram.bot.TelegramInformation
import org.telegram.bot.util.PriorityProducer
import org.telegram.bot.methods.AnswerHandler
import org.telegram.bot.methods.generateHttpPost

import org.json4s.jackson.JsonMethods.parse
import org.json4s.string2JsonInput
import org.json4s.jvalue2monadic


/**
 *
 */

class UpdateProducer(token: String, path: String, initialOffset: Int = 0, timeout: Int = 20)
                                extends PriorityProducer[Update] with TelegramInformation {

    private val log = BotLogger.getLogger(classOf[UpdateProducer].getName)

    private val httpClient = HttpClientBuilder.create
                                .setSSLHostnameVerifier(new NoopHostnameVerifier)
                                .setConnectionTimeToLive(timeout, TimeUnit.SECONDS).build

    private val url = telegramPath + token + "/" + path
    private var offset = initialOffset

    def run(): Unit = {
        while(true) {

            val pairs = generateUpdatePairs(offset, 100, timeout)
            val httpPost = generateHttpPost(url, pairs)

            debug(httpPost, pairs)

            try {
                val updateList = fetchUpdates(httpPost)
                addUpdatesToQueue(updateList)
                updateOffset(updateList)
            } catch {
                case cp: ClientProtocolException =>
            } finally {
                waitForUpdates
            }
        }
    }

    def updateOffset(): Int = offset

    private def fetchUpdates(httpPost: HttpPost): List[Update] = {
        val response = httpClient.execute(httpPost, new AnswerHandler)
        val json = parse(response) \ "result"

        for{ child <- json.children }
            yield { new Update(child) }
    }

    private def addUpdatesToQueue(updateList: List[Update]) = {
        updateList.filter(_.update_id > offset).foreach { this.put(_) }
    }

    private def updateOffset(updateList: List[Update]) = {
        val off = updateList.filter{ x => updateList.forall(_.update_id <= x.update_id) }
        offset = if(off.length > 0) off(0).update_id else offset
    }

    private def waitForUpdates() = {
        try {
            this.synchronized{
                this wait 500
            }
        } catch {
            case ie: InterruptedException => log.error(ie)
        }
    }

    private def debug(http: HttpPost, nameValuePairs: List[NameValuePair]) = {
        log.debug(http.toString)
        log.debug(nameValuePairs.toString)
    }
}
