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

trait OutgoingData {

    def chatId(): Int

    def replyMessageId(): Int

    def replayMarkup(): ReplyKeyboard

    def buildPairsList(): List[NameValuePair] = {
        buildPair(OutgoingDataField.chatId, chatId.toString) ::
        buildPair(OutgoingDataField.replyToMessageId, replyMessageId.toString) ::
        buildPair(OutgoingDataField.replyMarkup, replayMarkup.toString) ::
        Nil
    }

    protected def buildPair(field: String, content: String): NameValuePair = new BasicNameValuePair(field, content)

}

object OutgoingDataField {

    val chatId = "chat_id"

    val replyToMessageId = "reply_to_message_id"

    val replyMarkup = "reply_markup"

}
