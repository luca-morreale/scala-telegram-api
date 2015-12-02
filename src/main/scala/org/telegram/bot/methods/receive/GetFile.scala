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

import java.io.{ File => JFile, FileOutputStream }
import java.net.URL

import scala.collection.immutable.HashMap
import scala.sys.process.urlToProcess
import scala.io.BufferedSource
import scala.io.Source

import org.telegram.bot.api.File
import org.telegram.bot.util.BotLogger
import org.telegram.bot.methods.BaseMethod
import org.telegram.bot.methods.MethodDebugger
import org.telegram.bot.methods.buildValuePairs
import org.telegram.bot.methods.pairsToEntity
import org.telegram.bot.methods.generateHttpPost

import spray.can.Http

/**
 *
 */

class GetFile(token: String) extends BaseMethod(token) with MethodDebugger {

    override def url(): String = super.url + token + "/" + "getfile"

    def request(file_id: Int): Option[File] = {
        val pairs = buildValuePairs(HashMap("file_id" -> file_id.toString))
        val entity = pairsToEntity(pairs)
        val httpPost = generateHttpPost(url, entity)

        debug(httpPost, entity)

        handleAnswer[File](httpClient, httpPost)
    }

    def get(file_id: Int, fileName: String): JFile = {
        val apiFile = request(file_id)

        if(apiFile.isDefined) {
            (new URL(apiFile.get.getFullPath(token)) #> new JFile(fileName)).!!
            new JFile(fileName)
        } else {
            throw new Exception("impossible to downlod the file")
        }

    }

    def getBufferedSource(file_id: Int, fileName: String): BufferedSource = {
        val apiFile = request(file_id)

        if(apiFile.isDefined) {
            val url = new URL(apiFile.get.getFullPath(token))
            Source.fromURL(url)
        } else {
            throw new Exception("impossible to downlod the file")
        }
    }
}
