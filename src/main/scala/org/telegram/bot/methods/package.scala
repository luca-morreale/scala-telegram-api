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

package org.telegram.bot

import org.apache.http.NameValuePair
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpPost
import org.apache.http.message.BasicNameValuePair

import scala.collection.JavaConverters.seqAsJavaListConverter
import scala.collection.immutable.HashMap

/**
 *
 */

package object methods {

    /**
     * Generates an HttPost class with base header and the given parameters.
     * @params url              url of the server
     * @params nameValuePairs   post parameters
     */
    def generateHttpPost(url: String, nameValuePairs: List[NameValuePair]):HttpPost = {
        val http = generateHttpPost(url)
        http.setEntity(new UrlEncodedFormEntity(nameValuePairs.asJava, "UTF-8"))
        http
    }

     def generateHttpPost(url: String):HttpPost = {
        val http = new HttpPost(url)
        http.addHeader("Content-type", "application/x-www-form-urlencoded")
        http.addHeader("charset", "UTF-8")
        http
    }

    def buildValuePairs(pairs: HashMap[String, String]): List[NameValuePair] = {
        val values = for{ value <- pairs }
                    yield { new BasicNameValuePair(value._1, value._2) }

        values.asInstanceOf[List[NameValuePair]]
    }
}
