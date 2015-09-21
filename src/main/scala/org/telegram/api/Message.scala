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

package org.telegram.api

import org.json4s.JValue
import org.json4s.JField

import java.util.Date

/**
 *
 */


class Message(json: JValue) {

    val message_id: Long = (json \ Message.MESSAGEID_FIELD).extract[Long]

    val from: User = (json \ Message.FROM_FIELD).extract[User]

    val date: Int = (json \ Message.DATE_FIELD).extract[Int]

    val chat: Chat = (json \ Message.CHAT_FIELD).extract[Chat]

    val forward_from: Option[User] =
            if (fieldExists(json, Message.FORWARDFROM_FIELD)) Some((json \ Message.FORWARDFROM_FIELD).extract[User]) else None

    val forward_date: Option[Int] =
            if (fieldExists(json, Message.FORWARDDATE_FIELD)) Some((json \ Message.FORWARDDATE_FIELD).extract[Int]) else None

    val text: Option[String] =
            if (fieldExists(json, Message.TEXT_FIELD)) Some((json \ Message.TEXT_FIELD).extract[String]) else None

    val audio: Option[Audio] =
            if (fieldExists(json, Message.AUDIO_FIELD)) Some((json \ Message.AUDIO_FIELD).extract[Audio]) else None

    val document: Option[Document] =
            if (fieldExists(json, Message.DOCUMENT_FIELD)) Some((json \ Message.DOCUMENT_FIELD).extract[Document]) else None

    val photo: Option[List[PhotoSize]] =
            if (fieldExists(json, Message.PHOTO_FIELD)) Some((json \ Message.PHOTO_FIELD).extract[List[PhotoSize]]) else None

    val sticker: Option[Sticker] =
            if (fieldExists(json, Message.STICKER_FIELD)) Some((json \ Message.STICKER_FIELD).extract[Sticker]) else None

    val video: Option[Video] =
            if (fieldExists(json, Message.VIDEO_FIELD)) Some((json \ Message.VIDEO_FIELD).extract[Video]) else None

    val voice: Option[Voice] =
            if (fieldExists(json, Message.VOICE_FIELD)) Some((json \ Message.VOICE_FIELD).extract[Voice]) else None

    val caption: Option[String] =
            if (fieldExists(json, Message.CAPTION_FIELD)) Some((json \ Message.CAPTION_FIELD).extract[String]) else None

    val contact: Option[Contact] =
            if (fieldExists(json, Message.CONTACT_FIELD)) Some((json \ Message.CONTACT_FIELD).extract[Contact]) else None

    val location: Option[Location] =
            if (fieldExists(json, Message.LOCATION_FIELD)) Some((json \ Message.LOCATION_FIELD).extract[Location]) else None

    val new_chat_participant: Option[User] =
            if (fieldExists(json, Message.NEWCHATPARTICIPANT_FIELD)) Some((json \ Message.NEWCHATPARTICIPANT_FIELD).extract[User]) else None

    val left_chat_participant: Option[User] =
            if (fieldExists(json, Message.LEFTCHATPARTICIPANT_FIELD)) Some((json \ Message.LEFTCHATPARTICIPANT_FIELD).extract[User]) else None

    val reply_to_message: Option[Message] =
            if (fieldExists(json, Message.REPLYTOMESSAGE_FIELD)) Some((json \ Message.REPLYTOMESSAGE_FIELD).extract[Message]) else None

    val new_chat_title: Option[String] =
            if (fieldExists(json, Message.NEWCHATTITLE_FIELD)) Some((json \ Message.NEWCHATTITLE_FIELD).extract[String]) else None

    val new_chat_photo: Option[List[PhotoSize]]  =
            if (fieldExists(json, Message.NEWCHATPHOTO_FIELD)) Some((json \ Message.NEWCHATPHOTO_FIELD).extract[List[PhotoSize]]) else None

    val delete_chat_photo: Option[Boolean] =
            if (fieldExists(json, Message.DELETECHATPHOTO_FIELD)) Some((json \ Message.DELETECHATPHOTO_FIELD).extract[Boolean]) else None

    val group_chat_created: Option[Boolean] =
            if (fieldExists(json, Message.GROUPCHATCREATED_FIELD)) Some((json \ Message.GROUPCHATCREATED_FIELD).extract[Boolean]) else None

    def getConvertedDate():Date = new Date((date * 1000) toLong)

    def isGroupMessage():Boolean = chat.isGroupChat

    def getChatId():Int = chat.id

    def getForwardDateConverted():Date = new Date((forward_date.get * 1000) toLong)

    def hasText():Boolean = text != None

    def hasDocument():Boolean = document != None

    def hasReplayMessage():Boolean = reply_to_message != None

    def isReply():Boolean = this.reply_to_message != None

    def hasLocation():Boolean = location != None

    def compare(that: Message): Int =  this.date - that.date

    override def toString(): String = "Message [message_id: " + message_id + ", from: " + from + ", date: " + date +
                                ", chat: " + chat + ", forward_from: " + forward_from +
                                ", forward_date: " + forward_date + ", reply_to_message: " + reply_to_message +
                                ", text: " + text + ", audio: " + audio + ", document: " + document +
                                ", photo: " + photo + ", sticker: " + sticker + ", video: " + video +
                                ", voice: " + voice + ", caption: " + caption + ", contact: " + contact +
                                ", location: " + location + ", new_chat_participant: " + new_chat_participant +
                                ", left_chat_participant: " + left_chat_participant + ", new_chat_title: " + new_chat_title +
                                ", new_chat_photo: " + new_chat_photo + ", deleteChatPhoto: " + delete_chat_photo + "]"
}


protected object Message {

    val MESSAGEID_FIELD = "message_id";

    val FROM_FIELD = "from";

    val DATE_FIELD = "date";

    val CHAT_FIELD = "chat";

    val FORWARDFROM_FIELD = "forward_from";

    val FORWARDDATE_FIELD = "forward_date";

    val TEXT_FIELD = "text";

    val AUDIO_FIELD = "audio";

    val DOCUMENT_FIELD = "document";

    val PHOTO_FIELD = "photo";

    val STICKER_FIELD = "sticker";

    val VIDEO_FIELD = "video";

    val VOICE_FIELD = "voice"

    val CAPTION_FIELD = "caption"

    val CONTACT_FIELD = "contact";

    val LOCATION_FIELD = "location";

    val NEWCHATPARTICIPANT_FIELD = "new_chat_participant";

    val LEFTCHATPARTICIPANT_FIELD = "left_chat_participant";

    val NEWCHATTITLE_FIELD = "new_chat_title";

    val NEWCHATPHOTO_FIELD = "new_chat_photo";

    val DELETECHATPHOTO_FIELD = "delete_chat_photo";

    val GROUPCHATCREATED_FIELD = "group_chat_created";

    val REPLYTOMESSAGE_FIELD = "reply_to_message";
}

