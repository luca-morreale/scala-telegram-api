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

import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.client.ClientProtocolException
import org.apache.http.HttpEntity
import org.telegram.bot.methods.send.exception.SendingException
import org.telegram.bot.methods.AnswerHandler
import org.telegram.bot.methods.MethodDebugger
import org.telegram.bot.methods.generateHttpPost
import org.telegram.bot.methods.pairsToEntity
import org.telegram.bot.util.Consumer
import java.io.IOException

import org.telegram.bot.methods.send.data.OutgoingData


/**
 * Trait containing the base methods to send message to every possible type of service
 */

trait DataSender[T <: OutgoingData] extends MethodDebugger with Consumer[T] {

    /**
     * URL of the service where deliver the message
     *
     * @return      the url of the service
     */
    def url(): String

    /**
     * Body of the trait, perform a continuous polling over the internal queue, and sends the message polled
     */
    override def run():Unit = {
        while(true) {
            val out = this.get

            try {
                send(out)
            } catch {
                case ioe: IOException =>
                    logger error ioe
                    accept(out)
                    throw new SendingException
                case _: ClientProtocolException =>

            }
        }
    }

    /**
     * Delivers the message to the URL
     *
     * @param out   message to deliver
     */
    def send(out: OutgoingData): Unit = {
        val pairs = out.buildPairsList
        sendData(pairsToEntity(pairs))
    }

    protected def httpClient(): CloseableHttpClient

    /**
     * Tries to send the message, if after 10 times still fails it throws an exception
     *
     * @param entity            entity containing the whole information
     * @param resendCounter     counter of attempts
     */
    protected def sendData(entity: HttpEntity, resendCounter :Int=0): Unit = {
        if(resendCounter > 10) return

        try {
            val httpPost = generateHttpPost(url, entity)
            debug(httpPost, entity)

            httpClient.execute(httpPost, new AnswerHandler)
        } catch {
            case cp: ClientProtocolException =>
                waitForResend
                sendData(entity, resendCounter + 1)
        }
    }

    private def waitForResend() = {
        try {
            this.synchronized{
                this wait 100
            }
        } catch {
            case ie: InterruptedException => logger.error(ie)
        }
    }

}
