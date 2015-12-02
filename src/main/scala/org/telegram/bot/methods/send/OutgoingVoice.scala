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
import org.telegram.bot.methods.send.exception.EntityNotSupportedException

import org.apache.http.NameValuePair
import org.apache.http.entity.ContentType
import org.apache.http.entity.mime.MultipartEntityBuilder
import org.apache.http.entity.mime.content.{StringBody, FileBody}

import java.io.File

/**
 *
 */

class OutgoingVoice(chatId: Int,
                        voice: File,
                        duration: Option[Int],
                        replayToMessageId: Option[Int],
                        replayMarkup: Option[ReplyKeyboard]
                        ) extends OutgoingData(chatId, replayToMessageId, replayMarkup) {

    override def buildPairsList(): List[NameValuePair] = throw new EntityNotSupportedException("An audio message can not be sent using NameValuePair.")

    override def buildMultipart(): MultipartEntityBuilder = {
        optPart(OutgoingVoiceField.duration, duration,
            super.buildMultipart.addPart(OutgoingVoiceField.voice, new FileBody(voice))
        )
    }

}

object OutgoingVoiceField {

    val voice = "voice"

    val duration = "duration"

}
