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

import org.telegram.bot.methods.MethodDebugger
import org.telegram.bot.methods.pairsToEntity
import org.telegram.bot.methods.generateHttpPost

import org.apache.http.NameValuePair

/**
 * Created by luca on 22/04/16.
 */
abstract class BaseGetMethod(token: String) extends DataReceiver(token) with MethodDebugger {

    /**
     *  Builds the request and sends it to the actual url of the Get method
     *
     *  @param pairs        pairs to be included in the request
     *  @return             option of the specified API classs extracted from the response
     */
    def request[T >: Null: Manifest](pairs: List[NameValuePair]): Option[T] = {

        val entity = pairsToEntity(pairs)
        val httpPost = generateHttpPost(url, entity)

        debug(httpPost, entity)

        handleAnswer[T](httpClient, httpPost)
    }

    /**
     *  Builds the request and sends it to the actual url of the Get method
     *
     *  @return             option of the specified API classs extracted from the response
     */
    def request[T >: Null: Manifest](): Option[T] = {

        val httpPost = generateHttpPost(url)

        debug(httpPost)

        handleAnswer[T](httpClient, httpPost)
    }

}
