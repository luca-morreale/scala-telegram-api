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

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse
import org.junit.Test

import org.hamcrest.core.Is.is
import org.hamcrest.core.IsNot.not
import org.hamcrest.CoreMatchers.notNullValue

import org.json4s.jackson.JsonMethods.parse
import org.json4s.string2JsonInput
import org.json4s.jvalue2monadic


class APITest {

    @Test def extractAPITest(): Unit = {
        val json = parse(
                """{"audio":{"file_id":"1sad", "duration":6, "title":"audio_test"}}"""
                )
        val audio = extractAPI[Audio](json, Message.AUDIO_FIELD)

        assertTrue(audio.isDefined)
        assertThat(audio.get.file_id, is("1sad"))
        assertThat(audio.get.duration, is(6))
        assertTrue(audio.get.title.isDefined)
        assertThat(audio.get.title.get, is("audio_test"))
    }

    @Test def fieldExistsTest(): Unit = {
        val json = parse(""" {"ok":true,"result":[{"update_id":371616082},{"update_id":371616083}]} """)

        assertTrue(fieldExists(json, "result"))
        assertFalse(fieldExists(json, "update"))

        val updateJson = (json\"result")(0)

        assertTrue(fieldExists(updateJson, "update_id"))
        assertFalse(fieldExists(updateJson, "message"))
    }

    @Test def messageTest(): Unit = {
        val jsonString = """{"message":{"message_id":3,
                "from":{"id":41538947,"first_name":"Luca","last_name":"Morreale"},
                "chat":{"id":41538947,"type":"group", "first_name":"Luca","last_name":"Morreale"},
                "date":1442834410,"text":"\/start"}}"""
        val json = parse(jsonString)

        val messageOpt: Option[Message] = apiFromJson[Message](json, Update.MESSAGE_FIELD)

        assertTrue(messageOpt.isDefined)
        val message = messageOpt.get

        val chatJson = parse("""{
                "id":41538947,
                "type":"group",
                "first_name":"Luca","last_name":"Morreale"}""")

        val chat = new Chat(chatJson)
        assertEquals(message.chat, chat)
    }

    @Test def completeTest(): Unit = {

        val json = parse(example) \ "result"
        val updateList = for{ child <- json.children } yield { new Update(child) }

        assertThat(updateList.length, is(2))

        val update = updateList(0)
        assertThat(update.update_id, is(371616082))
        assertThat(updateList(1).update_id, is(371616083))
        assertThat(update.message, is(notNullValue))

        val message = update.message.get

        assertThat(message.message_id, is(3))
        assertEquals(message.from, User(41538947, "Luca", Some("Morreale"), None))
        assertThat(message.date, is(1442834410))
        assertThat(message.text.get, is("/start"))

        assertEquals(message.audio, None)
        assertEquals(message.video, None)
        assertEquals(message.voice, None)
        assertEquals(message.photo, None)
        assertEquals(message.caption, None)

        assertEquals(message.contact, None)
        assertEquals(message.delete_chat_photo, None)
        assertEquals(message.document, None)
        assertEquals(message.forward_date, None)
        assertEquals(message.forward_from, None)

        assertEquals(message.location, None)
    }

    private val example = """
    {"ok":true,"result":[{"update_id":371616082,
        "message":{"message_id":3,
            "from":{"id":41538947,"first_name":"Luca","last_name":"Morreale"},
            "chat":{"id":41538947,"type":"chat","first_name":"Luca","last_name":"Morreale"},
            "date":1442834410,"text":"\/start"
        }
    },{"update_id":371616083,
        "message":{"message_id":4,
            "from":{"id":41538947,"first_name":"Luca","last_name":"Morreale"},
            "chat":{"id":41538947,"type":"chat","first_name":"Luca","last_name":"Morreale"},
            "date":1442834590,"text":"\/get Berlino"
        }
    }]}"""
}
