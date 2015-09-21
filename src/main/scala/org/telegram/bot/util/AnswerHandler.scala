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

package org.telegram.bot.util

import org.apache.http.HttpResponse
import org.apache.http.client.ClientProtocolException
import org.apache.http.client.ResponseHandler
import org.apache.http.util.EntityUtils;
import org.apache.http.entity.BufferedHttpEntity;

/**
 * Simple implementation of ResponseHandler class that just
 * check the status code of the incoming message and returns the
 * proper answer.
 *
 */

class AnswerHandler extends ResponseHandler[String] {

    /**
     * Checks if the incoming message has a successful status code
     * then it return the content, otherwise throws the appropriate exception.
     *
     * @param response      object representing the message.
     * @return String       the content of the answer.
     * @throws ClientProtocolException      if the message does not have a successful status code.
     */
    override def handleResponse(response: HttpResponse): String = {

        val status = response.getStatusLine().getStatusCode();
        if (status >= 200 && status < 300) {

            val entity = response.getEntity();
            val buf = new BufferedHttpEntity(entity);

            return if (entity != null) EntityUtils.toString(buf, "UTF-8") else null;
        } else {
            throw new ClientProtocolException("Unexpected response status: " + status);
        }
    }
}
