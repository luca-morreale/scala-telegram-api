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

import org.telegram.bot.methods.receive.getupdate.UpdateProducer
import org.telegram.bot.methods.generateHttpPost
import org.telegram.bot.TelegramInformation
import org.telegram.bot.methods.BaseMethod
import org.telegram.bot.util.BotLogger
import org.telegram.bot.api.User

import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.conn.ssl.NoopHostnameVerifier

import java.util.concurrent.TimeUnit

/**
 *
 */

class GetMe(token: String, timeout: Int, name: String) extends BaseMethod(token) {

    private val log = BotLogger.getLogger(classOf[GetMe].getName)

    override def path(): String = "getme"

    private val url = super.path + token + "/" + path

    def request(): Option[User] = {
        val httpPost = generateHttpPost(url)

        log.debug(httpPost.toString)
        handleAnswer[User](httpClient, httpPost)
    }

    def checkToken(): Boolean = {
        val userOpt = request
        if(userOpt == None) {
            false
        }else {
            userOpt.get.id.toString == token.split(":")(0) &&
                userOpt.get.first_name == name
        }
    }
}