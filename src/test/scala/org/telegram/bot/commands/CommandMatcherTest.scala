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

package org.telegram.bot.commands

import org.json4s.jackson.JsonMethods.parse
import org.json4s.string2JsonInput

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

import org.mockito.Captor
import org.mockito.Matchers
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

import org.telegram.bot.api.Message



class CommandMatcherTest {

    private val mockedReceiver = mock(classOf[CallbackExample])

    @Captor val messageCaptor = ArgumentCaptor.forClass(classOf[Message])

    private val matcher = new CommandMatcher("<name>").setStartCommand(mockedReceiver.start)
                                            .setStopCommand(mockedReceiver.stop)
                                            .addCommand("/my_command", mockedReceiver.testCommand)

    @Before def init(): Unit ={
        MockitoAnnotations.initMocks(this)
    }

    @Test def matchComamndTest(): Unit = {

        var msg = message("/start")

        matcher.matchCommand(msg)
        verify(mockedReceiver).start(messageCaptor.capture, Matchers.any())
        assertEquals(msg, messageCaptor.getValue)


        msg = message("/stop@<name>")

        matcher.matchCommand(msg)
        verify(mockedReceiver).stop(messageCaptor.capture, Matchers.any())
        assertEquals(msg, messageCaptor.getValue)

        msg = message("/my_command@<name> test message")

        matcher.matchCommand(msg)
        verify(mockedReceiver).testCommand(messageCaptor.capture, Matchers.eq("test message"))
        assertEquals(msg, messageCaptor.getValue)


        msg = message("/my_command test message")

        matcher.matchCommand(msg)
        verify(mockedReceiver, times(2)).testCommand(messageCaptor.capture, Matchers.eq("test message"))
        assertEquals(msg, messageCaptor.getValue)
    }


    private def message(body: String): Message = {

        val jsonString = """{"message_id":3,
                "from":{"id":41538947,"first_name":"Luca","last_name":"Morreale"},
                "chat":{"id":41538947,"type":"group", "first_name":"Luca","last_name":"Morreale"},
                "date":1442834410,
                "text":"""" + body + """"}"""
        val json = parse(jsonString)
        new Message(json)
    }


    private class CallbackExample {
        def start(msg: Message, text: String):Unit = {}
        def stop(msg: Message, text: String):Unit = {}
        def testCommand(msg: Message, text: String):Unit = {}
    }

}
