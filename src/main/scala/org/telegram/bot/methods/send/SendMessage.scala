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

import java.io.IOException

import org.telegram.bot.methods.BaseMethod
import org.telegram.bot.util.Consumer
import org.telegram.bot.util.BotLogger
import org.telegram.bot.methods.AnswerHandler
import org.telegram.bot.methods.generateHttpPost

/**
 *
 */

class SendMessage(token: String) extends BaseMethod(token) with Consumer[OutgoingMessage] {

    private val log = BotLogger.getLogger(classOf[SendMessage].getName)

    override def path(): String = "sendmessage"

    private val url = super.path + token + "/" + path

    override def run():Unit = {

        while(true) {
            val out = this.get
            try {
                val pairs = out.buildPairsList
                val httpPost = generateHttpPost(url, pairs)
                debug(httpPost, pairs)

                httpClient.execute(httpPost, new AnswerHandler)
            } catch {
                case ioe: IOException =>
                    log.error(ioe)
                    accept(out)
                    throw new SendingException
            }
        }
    }
}
