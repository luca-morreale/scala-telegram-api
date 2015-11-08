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

class Update(json: JValue) extends Ordered[Update] with APIClass {

    val update_id: Int = (json \ Update.UPDATEID_FIELD).extract[Int]

    val message: Option[Message] = apiFromJson[Message](json, Update.MESSAGE_FIELD)

    /**
     * ========================================================
     *
     *
     * ========================================================
     */

    override def equals(other: Any): Boolean = {
        other match {
            case o: Update => compare(o) == 0
            case _ => false
        }
    }

    override def hashCode(): Int = {
        val prime = 92821
        var result = update_id
        result * prime + message.hashCode
    }

    def compare(that: Update): Int = update_id - that.update_id

    override def toString():String = "Update [update_id: " + update_id + ", " + message + "]"
}

protected object Update {

    val UPDATEID_FIELD = "update_id"

    val MESSAGE_FIELD = "message"

}
