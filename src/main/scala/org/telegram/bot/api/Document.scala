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

case class Document(
        val file_id: String,
        val thumb: Option[PhotoSize],
        val file_name: Option[String],
        val mime_type: Option[String],
        val file_size: Option[Int]
                    ) extends Ordered[Document] with APIClass {

    def compare(that: Document):Int = file_id compareTo that.file_id

    override def toString(): String = "Document [file_id: " + file_id + ", thumb: " + thumb + ", file_name: " + file_name +
                                    ", mime_type: "  + mime_type + ", file_size: " + file_size + "]"

}
