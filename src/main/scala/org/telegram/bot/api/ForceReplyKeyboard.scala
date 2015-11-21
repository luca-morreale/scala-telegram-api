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
import org.json4s.JsonDSL.boolean2jvalue
import org.json4s.JsonDSL.jobject2assoc
import org.json4s.JsonDSL.option2jvalue
import org.json4s.JsonDSL.pair2Assoc
import org.json4s.JsonDSL.pair2jvalue
import org.json4s.JsonDSL.string2jvalue
import org.json4s.JsonDSL.seq2jvalue
import org.json4s.jvalue2extractable
import org.json4s.jvalue2monadic

/**
 * Upon receiving a message with this object, Telegram clients will display a reply interface to the user
 */

class ForceReplyKeyboard(json: JValue) extends ReplyKeyboard {

    /**
     * Shows reply interface to the user, as if they manually selected the bot‘s message and tapped ’Reply'
     */
    val forceReply: Boolean = (json \ ForceReplyKeyboard.FORCEREPLY_FIELD).extract[Boolean]

    /**
     * Use this parameter if you want to force reply from specific users only.
     * Targets:
     *      1) users that are @mentioned in the text of the Message object;
     *      2) if the bot's message is a reply (has reply_to_message_id), sender of the original message.
     */
    val selective: Boolean = (json \ ForceReplyKeyboard.SELECTIVE_FIELD).extract[Boolean]

    def this() {
        this(
            (ForceReplyKeyboard.FORCEREPLY_FIELD -> true) ~
                (ForceReplyKeyboard.SELECTIVE_FIELD -> false)
        )
    }

    def toJson(): JValue = {
        (ForceReplyKeyboard.FORCEREPLY_FIELD -> forceReply) ~
                (ForceReplyKeyboard.SELECTIVE_FIELD -> selective)
    }

}

protected object ForceReplyKeyboard {

    val FORCEREPLY_FIELD = "force_reply"

    val SELECTIVE_FIELD = "selective"
}
