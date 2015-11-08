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

package org.telegram.bot.api

import org.json4s.JValue
import org.json4s.JField

import java.util.Date

/**
 *
 */


class Message(json: JValue) extends Ordered[Message] {

    val message_id: Int = (json \ Message.MESSAGEID_FIELD).extract[Int]

    val from: User = (json \ Message.FROM_FIELD).extract[User]

    val date: Int = (json \ Message.DATE_FIELD).extract[Int]

    val chat: Chat = apiFromJson[Chat](json, Message.CHAT_FIELD).get

    val forward_from: Option[User] = extractAPI[User](json, Message.FORWARDFROM_FIELD)

    val forward_date: Option[Int] = extractAPI[Int](json, Message.FORWARDDATE_FIELD)

    val text: Option[String] = extractAPI[String](json, Message.TEXT_FIELD)

    val audio: Option[Audio] = extractAPI[Audio](json, Message.AUDIO_FIELD)

    val document: Option[Document] = extractAPI[Document](json, Message.DOCUMENT_FIELD)

    val photo: Option[List[PhotoSize]] = extractAPI[List[PhotoSize]](json, Message.PHOTO_FIELD)

    val sticker: Option[Sticker] = extractAPI[Sticker](json, Message.STICKER_FIELD)

    val video: Option[Video] = extractAPI[Video](json, Message.VIDEO_FIELD)

    val voice: Option[Voice] = extractAPI[Voice](json, Message.VOICE_FIELD)

    val caption: Option[String] = extractAPI[String](json, Message.CAPTION_FIELD)

    val contact: Option[Contact] = extractAPI[Contact](json, Message.CONTACT_FIELD)

    val location: Option[Location] = extractAPI[Location](json, Message.LOCATION_FIELD)

    val new_chat_participant: Option[User] = extractAPI[User](json, Message.NEWCHATPARTICIPANT_FIELD)

    val left_chat_participant: Option[User] = extractAPI[User](json, Message.LEFTCHATPARTICIPANT_FIELD)

    val reply_to_message: Option[Message] = extractAPI[Message](json, Message.REPLYTOMESSAGE_FIELD)

    val new_chat_title: Option[String] = extractAPI[String](json, Message.NEWCHATTITLE_FIELD)

    val new_chat_photo: Option[List[PhotoSize]] = extractAPI[List[PhotoSize]](json, Message.NEWCHATPHOTO_FIELD)

    val delete_chat_photo: Option[Boolean] = extractAPI[Boolean](json, Message.DELETECHATPHOTO_FIELD)

    val group_chat_created: Option[Boolean] = extractAPI[Boolean](json, Message.GROUPCHATCREATED_FIELD)

    /**
     * ========================================================
     *
     *
     * ========================================================
     */

    def getConvertedDate():Date = new Date((date * 1000) toLong)

    def isGroupMessage():Boolean = chat.isGroupChat

    def getChatId():Int = chat.id

    def getForwardDateConverted():Date = new Date((forward_date.get * 1000) toLong)

    def hasText():Boolean = text != None

    def hasDocument():Boolean = document != None

    def hasReplayMessage():Boolean = reply_to_message != None

    def isReply():Boolean = this.reply_to_message != None

    def hasLocation():Boolean = location != None

    override def equals(other: Any): Boolean = {
        other match {
            case o: Message => compare(o) == 0
            case _ => false
        }
    }

    override def hashCode(): Int = {
        val prime = 92821
        var result = message_id
        result = result * prime + from.hashCode
        result = result * prime + date
        result = result * prime + chat.hashCode
        result = result * prime + forward_from.hashCode
        result = result * prime + forward_date.hashCode
        result = result * prime + text.hashCode
        result = result * prime + audio.hashCode
        result = result * prime + document.hashCode
        result = result * prime + photo.hashCode
        result = result * prime + sticker.hashCode
        result = result * prime + video.hashCode
        result = result * prime + voice.hashCode
        result = result * prime + caption.hashCode
        result = result * prime + contact.hashCode
        result = result * prime + location.hashCode
        result = result * prime + new_chat_participant.hashCode
        result = result * prime + left_chat_participant.hashCode
        result = result * prime + reply_to_message.hashCode
        result = result * prime + new_chat_title.hashCode
        result = result * prime + new_chat_photo.hashCode
        result = result * prime + delete_chat_photo.hashCode
        result * prime + group_chat_created.hashCode
    }

    def compare(that: Message): Int = {
        if(date - that.date == 0) {
            message_id - that.message_id
        } else {
            date - that.date
        }
    }

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

    val MESSAGEID_FIELD = "message_id"

    val FROM_FIELD = "from"

    val DATE_FIELD = "date"

    val CHAT_FIELD = "chat"

    val FORWARDFROM_FIELD = "forward_from"

    val FORWARDDATE_FIELD = "forward_date"

    val TEXT_FIELD = "text"

    val AUDIO_FIELD = "audio"

    val DOCUMENT_FIELD = "document"

    val PHOTO_FIELD = "photo"

    val STICKER_FIELD = "sticker"

    val VIDEO_FIELD = "video"

    val VOICE_FIELD = "voice"

    val CAPTION_FIELD = "caption"

    val CONTACT_FIELD = "contact"

    val LOCATION_FIELD = "location"

    val NEWCHATPARTICIPANT_FIELD = "new_chat_participant"

    val LEFTCHATPARTICIPANT_FIELD = "left_chat_participant"

    val NEWCHATTITLE_FIELD = "new_chat_title"

    val NEWCHATPHOTO_FIELD = "new_chat_photo"

    val DELETECHATPHOTO_FIELD = "delete_chat_photo"

    val GROUPCHATCREATED_FIELD = "group_chat_created"

    val REPLYTOMESSAGE_FIELD = "reply_to_message"
}

