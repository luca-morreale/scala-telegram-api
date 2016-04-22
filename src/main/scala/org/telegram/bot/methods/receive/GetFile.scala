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

import java.io.{ File => JFile }
import java.net.URL

import scala.collection.immutable.HashMap
import scala.sys.process.urlToProcess
import scala.io.BufferedSource
import scala.io.Source

import org.telegram.bot.api.File
import org.telegram.bot.methods.MethodDebugger
import org.telegram.bot.methods.buildValuePairs


/**
 * Class which provides the getfile method.
 */

class GetFile(token: String) extends BaseGetMethod(token) {

    override def url(): String = super.url + token + "/" + "getfile"

    /**
     * Performs a request to telegram asking for the specified file,
     * in case of successful request it returns the API File class.
     *
     * @param file_id   identifier of the file
     * @return          in case of positive answer returns an API File class
     */
    def getFile(file_id: Int): Option[File] = {
        val pairs = buildValuePairs(HashMap("file_id" -> file_id.toString))
        request[File](pairs)
    }

    /**
     * Downloads a file form the telegram site and returns it.
     * @param file_id       identifier of the file
     * @param fileName      name of the file
     * @return              reference to the file saved
     */
    def downloadFile(file_id: Int, fileName: String): JFile = {
        val apiFile = getFile(file_id)

        if(apiFile.isDefined) {
            (new URL(apiFile.get.getFullPath(token)) #> new JFile(fileName)).!!
            new JFile(fileName)
        } else {
            throw new Exception("impossible to downlod the file")
        }
    }

    /**
     * Downloads a file for the telegram site and returns a BufferedSource reference.
     * @param file_id       identifier of the file
     * @param fileName      name of the file
     * @return              reference to the file saved
     */
    def getBufferedSource(file_id: Int, fileName: String): BufferedSource = {
        val apiFile = getFile(file_id)

        if(apiFile.isDefined) {
            val url = new URL(apiFile.get.getFullPath(token))
            Source.fromURL(url)
        } else {
            throw new Exception("impossible to downlod the file")
        }
    }
}
