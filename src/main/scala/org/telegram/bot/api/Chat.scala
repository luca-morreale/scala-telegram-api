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

import org.json4s.JValue

/**
 *
 */

class Chat(json: JValue) extends Ordered[Chat] {

    val id: Int = (json \ "id").extract[Int]

    val chat_type: String = (json \ "type").extract[String]

    val title: Option[String] = extractAPI[String](json, "title")

    val first_name: Option[String] = extractAPI[String](json, "first_name")

    val last_name: Option[String] = extractAPI[String](json, "last_name")

    val username: Option[String] = extractAPI[String](json, "username")

    override def equals(other: Any): Boolean = {
        other match {
            case o: Chat => compare(o) == 0
            case _ => false
        }
    }

    override def hashCode(): Int = {
        val prime = 92821
        var result = id
        result = result * prime + chat_type.hashCode
        result = result * prime + title.hashCode
        result = result * prime + first_name.hashCode
        result = result * prime + last_name.hashCode
        result * prime + username.hashCode
    }

    def compare(that: Chat):Int = id - that.id

    def isGroupChat():Boolean = chat_type == "group"

    override def toString(): String = "Chat [id: " + id + ", type: " + chat_type + ", title: " + title +
                                    ", first_name: " + first_name + ", last_name: " + last_name + ", username: " + username + "]"
}

