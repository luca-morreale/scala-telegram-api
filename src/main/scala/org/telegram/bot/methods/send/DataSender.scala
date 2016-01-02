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

package org.telegram.bot.methods.send

import org.apache.http.NameValuePair
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.HttpEntity

import org.telegram.bot.methods.AnswerHandler
import org.telegram.bot.methods.MethodDebugger
import org.telegram.bot.methods.generateHttpPost
import org.telegram.bot.methods.pairsToEntity
import org.telegram.bot.util.Consumer

import java.io.IOException

/**
 *
 */

trait DataSender[T <: OutgoingData] extends MethodDebugger with Consumer[T] {

    def url(): String

    override def run():Unit = {
        val out = this.get
        while(true) {

            try {
                send(out)
            } catch {
                case ioe: IOException =>
                    logger.error(ioe)
                    accept(out)
                    throw new SendingException
            }
        }
    }

    def send(out: OutgoingData): Unit = {

        val pairs = out.buildPairsList
        sendData(pairsToEntity(pairs))
    }

    protected def httpClient(): CloseableHttpClient

    protected def debug(http: HttpPost, entity: HttpEntity): Unit

    protected def sendData(entity: HttpEntity) = {

        val httpPost = generateHttpPost(url, entity)
        debug(httpPost, entity)

        httpClient.execute(httpPost, new AnswerHandler)
    }

}
