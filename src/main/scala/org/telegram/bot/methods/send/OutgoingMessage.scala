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

import org.apache.http.message.BasicNameValuePair
import org.apache.http.NameValuePair

/**
 *
 */

class OutgoingMessage(
        chatId: Int,
        text: String,
        disableWebPagePreview: Boolean,
        replayToMessageId: Int) {

    def buildPairs(): List[NameValuePair] = {
        List(
            new BasicNameValuePair(OutgoingMessage.CHATID_FIELD, chatId.toString),
            new BasicNameValuePair(OutgoingMessage.TEXT_FIELD, text),
            new BasicNameValuePair(OutgoingMessage.DISABLEWEBPAGEPREVIEW_FIELD, disableWebPagePreview.toString),
            new BasicNameValuePair(OutgoingMessage.REPLYTOMESSAGEID_FIELD, replayToMessageId.toString)
        )
    }
}

object OutgoingMessage {

    val CHATID_FIELD = "chat_id"

    val TEXT_FIELD = "text"

    val DISABLEWEBPAGEPREVIEW_FIELD = "disable_web_page_preview"

    val REPLYTOMESSAGEID_FIELD = "reply_to_message_id"

    val REPLYMARKUP_FIELD = "reply_markup"

}

