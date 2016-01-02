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
import org.telegram.bot.methods.MethodDebugger
import org.telegram.bot.util.BotLogger
import org.telegram.bot.api.User

import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.conn.ssl.NoopHostnameVerifier

import java.util.concurrent.TimeUnit

/**
 * Class which provides the getme method.
 */

class GetMe(token: String, timeout: Int, name: String) extends DataReceiver(token) with MethodDebugger {

    override def url(): String = super.url + token + "/" + "getme"

    /**
     * Sends a request to telegram's getme method.
     *
     * @param file_id   identifier of the file
     * @return          in case of positive answer returns an API File class
     */
    def request(): Option[User] = {
        val httpPost = generateHttpPost(url)

        debug(httpPost)
        handleAnswer[User](httpClient, httpPost)
    }

    /**
     * Checks the token given and the one received form telegram
     * are the same.
     */
    def checkToken(): Boolean = {
        val userOpt = request
        if(userOpt == None) {
            throw new Exception("impossible to check")
        }else {
            userOpt.get.id.toString == token.split(":")(0) &&
                userOpt.get.first_name == name
        }
    }
}
