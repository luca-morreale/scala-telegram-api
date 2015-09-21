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
import org.json4s.JsonDSL.option2jvalue
import org.json4s.JsonDSL.pair2Assoc
import org.json4s.jvalue2extractable
import org.json4s.jvalue2monadic

/**
 * Telegram clients will hide the current custom keyboard and display the default letter-keyboard.
 * By default, custom keyboards are displayed until a new keyboard is sent by a bot.
 * An exception is made for one-time keyboards that are hidden immediately after the user presses a button.
 */

class ReplyKeyboardHide(json: JValue) extends ReplyKeyboard {

    val hideKeyboard = (json \ ReplyKeyboardHide.HIDEKEYBOARD_FIELD).extractOpt[Boolean]

    /**
     * Optional. Use this parameter if you want to show the keyboard to specific users only.
     * Targets:
     *      1) users that are @mentioned in the text of the Message object;
     *      2) if the bot's message is a reply (has reply_to_message_id), sender of the original message.
     */
    val selective = (json \ ReplyKeyboardHide.HIDEKEYBOARD_FIELD).extractOpt[Boolean]

    def this() {
        this( (ReplyKeyboardHide.HIDEKEYBOARD_FIELD -> true) ~
                (ReplyKeyboardHide.SELECTIVE_FIELD -> true)
            )
    }

    override def toJson(): JValue = {
        (ReplyKeyboardHide.HIDEKEYBOARD_FIELD -> hideKeyboard) ~
            (ReplyKeyboardHide.SELECTIVE_FIELD -> selective)
    }

}


protected object ReplyKeyboardHide {

    val HIDEKEYBOARD_FIELD = "hide_keyboard"

    val SELECTIVE_FIELD = "selective"
}

