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

class Update(json: JValue) {

    /**
     * The update‘s unique identifier.
     * Update identifiers start from a certain positive number and increase sequentially.
     * This ID becomes especially handy if you’re using Webhooks,
     * since it allows you to ignore repeated updates or to restore the
     * correct update sequence, should they get out of order.
     */
    val updateID: Long = (json \ Update.UPDATEID_FIELD).extract[Long]

    val message: Option[Message] = if(json \ Update.MESSAGE_FIELD != None) Some(new Message(json \ Update.MESSAGE_FIELD)) else None

    override def toString():String = "Update [update_id: " + updateID + ", " + message + "]"
}

object Update {

    val UPDATEID_FIELD = "update_id"

    val MESSAGE_FIELD = "message"

}
