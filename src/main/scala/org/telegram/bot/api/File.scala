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

package org.telegram.bot.api

/**
 *
 */

case class File(
        val file_id: String,
        val file_size: Option[Int],
        val file_path: Option[String]) extends Ordered[File] with APIClass {

    def getFullPath(token: String): String = {
         if(file_path == None) throw new Exception("")
        "https://api.telegram.org/file/bot" + token + "/" + file_path.get
    }

    def compare(that: File):Int = file_id compareTo that.file_id

    override def toString(): String = "File [file_id: " + file_id + ", file_size: " + file_size +
                        ", file_path: " + file_path + "]"
}
