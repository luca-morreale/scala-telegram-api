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

package org.telegram.api

/**
 *
 */

case class Video(
        val file_id: String,
        val width: Int,
        val height:  Int,
        val duration: Int,
        val thumb: Option[PhotoSize],
        val mime_type: Option[String],
        val file_size: Option[Int]
        ) {

    override def toString(): String = "Video [file_id: " + file_id + ", width: " + width + ", height: " + height + ", duration: " + duration +
                            ", thumb: " + thumb + ", mime_type: " + mime_type + ", file_size: " + file_size + "]"
}

