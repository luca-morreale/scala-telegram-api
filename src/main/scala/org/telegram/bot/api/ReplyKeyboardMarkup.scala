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
 * This object represents a custom keyboard with reply options.
 */

class ReplyKeyboardMarkup(json: JValue) extends ReplyKeyboard {


    ///< Array of button rows, each represented by an Array of Strings
    val keyboard: List[String] = (json \ ReplyKeyboardMarkup.KEYBOARD_FIELD).extract[List[String]]

    ///< Optional. Requests clients to resize the keyboard vertically for optimal fit (e.g., make the keyboard smaller if there are just two rows of buttons). Defaults to false.
    val resizeKeyboard: Boolean = (json \ ReplyKeyboardMarkup.RESIZEKEYBOARD_FIELD).extract[Boolean]

     ///< Optional. Requests clients to hide the keyboard as soon as it's been used. Defaults to false.
    val oneTimeKeyboad: Boolean = (json \ ReplyKeyboardMarkup.ONETIMEKEYBOARD_FIELD).extract[Boolean]

    /**
     * Optional. Use this parameter if you want to show the keyboard to specific users only.
     * Targets:
     *      1) users that are @mentioned in the text of the Message object;
     *      2) if the bot's message is a reply (has reply_to_message_id), sender of the original message.
     */
    val selective: Boolean = (json \ ReplyKeyboardMarkup.SELECTIVE_FIELD).extract[Boolean]

    def this(fields: List[String]) {
        this(
            (ReplyKeyboardMarkup.KEYBOARD_FIELD -> fields) ~
                (ReplyKeyboardMarkup.RESIZEKEYBOARD_FIELD -> false) ~
                (ReplyKeyboardMarkup.ONETIMEKEYBOARD_FIELD -> false) ~
                (ReplyKeyboardMarkup.SELECTIVE_FIELD -> false)
            )
    }

    override def toJson(): JValue = {
        (ReplyKeyboardMarkup.KEYBOARD_FIELD -> keyboard) ~
                (ReplyKeyboardMarkup.RESIZEKEYBOARD_FIELD -> resizeKeyboard) ~
                (ReplyKeyboardMarkup.ONETIMEKEYBOARD_FIELD -> oneTimeKeyboad) ~
                (ReplyKeyboardMarkup.SELECTIVE_FIELD -> selective)
    }
}


protected object ReplyKeyboardMarkup{

    val KEYBOARD_FIELD = "keyboard"

    val RESIZEKEYBOARD_FIELD = "resize_keyboard"

    val ONETIMEKEYBOARD_FIELD = "one_time_keyboard"

    val SELECTIVE_FIELD = "selective"
}

