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

import java.io.File

import org.apache.http.NameValuePair
import org.apache.http.entity.mime.MultipartEntityBuilder
import org.apache.http.entity.mime.content.FileBody
import org.telegram.bot.api.ReplyKeyboard
import org.telegram.bot.methods.send.exception.EntityNotSupportedException

/**
 * An outgoing video message
 */

class OutgoingVideo(chatId: Int,
                        video: File,
                        duration: Option[Int],
                        caption: Option[String],
                        replayToMessageId: Option[Int],
                        replayMarkup: Option[ReplyKeyboard]
                        ) extends OutgoingData(chatId, replayToMessageId, replayMarkup) {

    override def buildPairsList(): List[NameValuePair] = throw new EntityNotSupportedException("A video message can not be sent using NameValuePair.")

    override def buildMultipart(): MultipartEntityBuilder = {
        optPart(OutgoingVideoField.duration, duration,
            optPart(OutgoingVideoField.caption, caption,
                    super.buildMultipart.addPart(OutgoingVideoField.video, new FileBody(video))
        ))
    }

}

object OutgoingVideoField {

    val video = "video"

    val duration = "duration"

    val caption = "caption"

}
