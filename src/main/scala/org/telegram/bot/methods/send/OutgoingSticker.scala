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

package org.telegram.bot.methods.send

import org.telegram.bot.api.ReplyKeyboard

import org.apache.http.message.BasicNameValuePair
import org.apache.http.NameValuePair


/**
 *
 */

case class OutgoingSticker(chatId: Int,
                            sticker: String,
                            replayToMessageId: Int,
                            replayMarkup: ReplyKeyboard
                            ) extends OutgoingData {

    private val stickerName: String = ""

}

object OutgoingSticker {

    val chatId = "chat_id"

    val sticker = "sticker"

    val replyToMessage_id = "reply_to_message_id"

    val replyMarkup = "reply_markup";
}
