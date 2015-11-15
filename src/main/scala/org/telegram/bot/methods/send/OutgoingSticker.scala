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

class OutgoingSticker(chatId: Int,
                        sticker: String,
                        replayToMessageId: Int,
                        replayMarkup: ReplyKeyboard
                        ) extends OutgoingData {

    private val stickerName: String = ""

    override def chatId(): Int = chatId

    override def replyMessageId(): Int = replayToMessageId

    override def replayMarkup(): ReplyKeyboard = replayMarkup

    override def buildPairsList(): List[NameValuePair] = buildPair(OutgoingStickerField.sticker, sticker) :: super.buildPairsList


}

object OutgoingStickerField {

    val sticker = "sticker"

}
