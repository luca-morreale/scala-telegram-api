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
import org.apache.http.client.methods.HttpPost
import org.apache.http.NameValuePair

import org.telegram.bot.api.Update
import org.telegram.bot.util.BotLogger
import org.telegram.bot.methods.pairsToEntity
import org.telegram.bot.util.PriorityProducer
import org.telegram.bot.methods.AnswerHandler
import org.telegram.bot.methods.MethodDebugger
import org.telegram.bot.methods.generateHttpPost
import org.telegram.bot.methods.receive.DataReceiver

import org.json4s.jackson.JsonMethods.parse
import org.json4s.string2JsonInput
import org.json4s.jvalue2monadic


/**
 *
 */

class UpdateProducer(token: String, initialOffset: Int = 0, timeout: Int)
                                extends DataReceiver(token, timeout) with MethodDebugger with PriorityProducer[Update] {

    override def url(): String = super.url + token + "/" + "getupdates"

    private var offset = initialOffset

    private val limit = 100

    /**
     * Main body of the class, send the requests and parse the incoming updates.
     */
    def run(): Unit = {
        while(true) {
            val post = generatePostRequest

            try {
                val updateList = fetchUpdates(post)
                addUpdatesToQueue(updateList)
                updateOffset(updateList)
            } catch {
                case cp: ClientProtocolException =>
            } finally {
                waitForUpdates
            }
        }
    }

    private def generatePostRequest(): HttpPost = {
        val entity = pairsToEntity(generateUpdatePairs(offset, limit, timeout))
        val post = generateHttpPost(url, entity)

        debug(post, entity)
        post
    }

    /**
     * Returns the last update id downloaded
     */
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
            case ie: InterruptedException => logger.error(ie)
        }
    }
}
