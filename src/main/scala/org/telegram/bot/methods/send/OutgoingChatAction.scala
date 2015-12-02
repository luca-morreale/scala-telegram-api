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

import org.apache.http.NameValuePair
import org.apache.http.entity.ContentType
import org.apache.http.entity.mime.content.StringBody
import org.apache.http.entity.mime.MultipartEntityBuilder

/**
 *
 */
class OutgoingChatAction(chatId: Int, action: String) extends OutgoingData(chatId, None, None) {

    override def buildPairsList(): List[NameValuePair] = buildPair(OutgoingChatActionField.action, action) :: super.buildPairsList

    override def buildMultipart(): MultipartEntityBuilder = super.buildMultipart
                                                                        .addPart(OutgoingChatActionField.action, new StringBody(action, ContentType.TEXT_PLAIN))
}

object OutgoingChatActionField {

    val action = "action"

}
