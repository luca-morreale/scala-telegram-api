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

import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.CloseableHttpClient
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Matchers.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.telegram.bot.methods.AnswerHandler
import org.telegram.bot.methods.generateHttpPost
import org.telegram.bot.methods.pairsToEntity
import org.telegram.bot.methods.send.data.OutgoingMessage

/**
 * This test is intended for all classes which use DataSender as parent trait,
 * because all of them use the same methods changing just the OutgoindData used.
 */

class SendMessageTest {

    private val mockedClient = mock(classOf[CloseableHttpClient])
    @Captor val captor: ArgumentCaptor[HttpPost] = ArgumentCaptor.forClass(classOf[HttpPost])

    private class SendMessageTestable(token: String) extends SendMessage(token) {
        override protected def httpClient(): CloseableHttpClient = mockedClient
    }

    @Test def testSendData(): Unit = {
        val sender = new SendMessageTestable("token")
        val msg = new OutgoingMessage(0, "text of the message", false)

        val entity = pairsToEntity(msg.buildPairsList)
        val httpPost = generateHttpPost(sender.url, entity)

        sender.send(msg)
        verify(mockedClient).execute(captor.capture, any(classOf[AnswerHandler]))

        // compare just toString because complete comparison will yield false
        assertEquals(httpPost.toString, captor.getValue().toString)
    }
}
