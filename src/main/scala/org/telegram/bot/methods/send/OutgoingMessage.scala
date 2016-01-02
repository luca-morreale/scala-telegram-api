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

import org.apache.http.NameValuePair
import org.apache.http.entity.ContentType
import org.apache.http.entity.mime.content.StringBody
import org.apache.http.entity.mime.MultipartEntityBuilder

/**
 *
 */

class OutgoingMessage(chatId: Int,
                        text: String,
                        disableWebPagePreview: Boolean,
                        replayToMessageId: Option[Int] = None,
                        replayMarkup: Option[ReplyKeyboard] = None
                        ) extends OutgoingData(chatId, replayToMessageId, replayMarkup) {

    override def buildPairsList(): List[NameValuePair] = {
        buildPair(OutgoingMessageField.text, text) ::
            buildPair(OutgoingMessageField.disableWebpagePreview, disableWebPagePreview.toString) ::
            super.buildPairsList
    }

    override def buildMultipart(): MultipartEntityBuilder = {
        super.buildMultipart.addPart(OutgoingMessageField.text, new StringBody(text, ContentType.TEXT_PLAIN))
            .addPart(OutgoingMessageField.disableWebpagePreview, new StringBody(disableWebPagePreview.toString, ContentType.TEXT_PLAIN))
    }
}

object OutgoingMessageField {

    val text = "text"

    val disableWebpagePreview = "disable_web_page_preview"

}

