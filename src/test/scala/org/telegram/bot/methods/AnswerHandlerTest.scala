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

package org.telegram.bot.methods

import org.apache.http.HttpEntity
import org.apache.http.HttpResponse
import org.apache.http.StatusLine
import org.apache.http.client.ClientProtocolException

import org.hamcrest.core.IsNull.nullValue
import org.junit.Assert.assertThat
import org.junit.Test

import org.mockito.Mockito.mock
import org.mockito.Mockito.when


class AnswerHandlerTest {

    private val handler = new AnswerHandler

    @Test def testHandler():Unit = {
        val mockedResponse = mock(classOf[HttpResponse])
        val mockedLine = mock(classOf[StatusLine])

        when(mockedResponse.getStatusLine()).thenReturn(mockedLine)
        when(mockedLine.getStatusCode()).thenReturn(200)
        when(mockedResponse.getEntity()).thenReturn(null)

        assertThat(handler.handleResponse(mockedResponse), nullValue())

        val mockedEntity = mock(classOf[HttpEntity])
        when(mockedResponse.getEntity()).thenReturn(mockedEntity)
        assertThat(handler.handleResponse(mockedResponse), nullValue())
    }


    @Test(expected=classOf[ClientProtocolException])
    def testHandlerException():Unit = {
        val mockedResponse = mock(classOf[HttpResponse])
        val mockedLine = mock(classOf[StatusLine])

        when(mockedResponse.getStatusLine()).thenReturn(mockedLine)
        when(mockedLine.getStatusCode()).thenReturn(400)
        handler.handleResponse(mockedResponse)
    }

}

