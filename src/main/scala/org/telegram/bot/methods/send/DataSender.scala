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

import org.telegram.bot.methods.AnswerHandler
import org.telegram.bot.methods.MethodDebugger
import org.telegram.bot.methods.generateHttpPost

/**
 *
 */

trait DataSender extends MethodDebugger{

    def url(): String

    protected def httpClient(): CloseableHttpClient

    protected def debug(http: HttpPost, nameValuePairs: List[NameValuePair]): Unit

    protected def send(out: OutgoingData): Unit = {

        val pairs = out.buildPairsList
        val httpPost = generateHttpPost(url, pairs)
        debug(httpPost, pairs)

        httpClient.execute(httpPost, new AnswerHandler)
    }
}
