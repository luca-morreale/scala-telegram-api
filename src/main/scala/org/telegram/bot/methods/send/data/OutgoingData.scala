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

package org.telegram.bot.methods.send.data

import org.apache.http.NameValuePair
import org.apache.http.entity.ContentType
import org.apache.http.entity.mime.MultipartEntityBuilder
import org.apache.http.entity.mime.content.StringBody
import org.apache.http.message.BasicNameValuePair
import org.telegram.bot.api.ReplyKeyboard

/**
 * Base implementation of an outgoing message
 */

abstract class OutgoingData(chatId: Int,
                            replayToMessageId: Option[Int]=None,
                            replayMarkup: Option[ReplyKeyboard]=None
                            ) {

    def buildPairsList(): List[NameValuePair] = buildPair(OutgoingDataField.chatId, chatId.toString) :: buildOptList

    def buildMultipart(): MultipartEntityBuilder = buildOptPart addPart(OutgoingDataField.chatId, new StringBody(chatId.toString, ContentType.TEXT_PLAIN))

    private def buildOptPart(): MultipartEntityBuilder = {
        optPart(OutgoingDataField.replayMarkup, replayMarkup,
            optPart(OutgoingDataField.replayToMessageId, replayToMessageId,
                MultipartEntityBuilder.create)
        )
    }

    protected def optPart(field: String, value: Option[Any], builder: MultipartEntityBuilder): MultipartEntityBuilder = {
        if(value.isDefined) {
            builder.addPart(field, new StringBody(value.get.toString, ContentType.TEXT_PLAIN))
        }
        builder
    }

    private def buildOptList(): List[NameValuePair] = {
        optPair(OutgoingDataField.replayToMessageId, replayToMessageId) :::
            optPair(OutgoingDataField.replayMarkup, replayMarkup)
    }

    protected def optPair(field: String, value: Option[Any]): List[NameValuePair] = {
        if(value.isDefined) {
            buildPair(field, value.get.toString) :: Nil
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
