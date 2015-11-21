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

abstract class OutgoingData(chatId: Int,
                            replayToMessageId: Option[Int]=None,
                            replayMarkup: Option[ReplyKeyboard]=None
                            ) {

    def buildPairsList(): List[NameValuePair] = buildPair(OutgoingDataField.chatId, chatId.toString) :: buildOptList

    private def buildOptList(): List[NameValuePair] = optReplayMessageIdList() ::: optReplayMarkupList()

    private def optReplayMessageIdList(): List[NameValuePair] = {
        if(replayToMessageId.isDefined) {
            buildPair(OutgoingDataField.replayToMessageId, replayToMessageId.get.toString) :: Nil
        } else {
            Nil
        }
    }

    private def optReplayMarkupList(): List[NameValuePair] = {
        if(replayMarkup.isDefined) {
            buildPair(OutgoingDataField.replayMarkup, replayMarkup.toString) :: Nil
        } else {
            Nil
        }
    }

    protected def buildPair(field: String, content: String): NameValuePair = new BasicNameValuePair(field, content)

}

object OutgoingDataField {

    val chatId = "chat_id"

    val replayToMessageId = "reply_to_message_id"

    val replayMarkup = "reply_markup"

}
