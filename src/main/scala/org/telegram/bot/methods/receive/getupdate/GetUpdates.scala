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

package org.telegram.bot.methods.receive.getupdate

import org.apache.http.NameValuePair
import org.apache.http.message.BasicNameValuePair

/**
 *
 */

protected class GetUpdates(
    /**
     * Optional Identifier of the first update to be returned.
     * Must be greater by one than the highest among the identifiers of previously received updates.
     * By default, updates starting with the earliest unconfirmed update are returned.
     */
    val offset: Int,

    /**
     * Optional Limits the number of updates to be retrieved.
     * Values between 1—100 are accepted. Defaults to 100
     */
    val limit: Int,

    /**
     * Optional Timeout in seconds for long polling. Defaults to 0, i.e. usual short polling
     */
    val timeout: Int
    ) {

    def this() = {
        this(GetUpdates.defaultOffset, GetUpdates.defaultLimit, GetUpdates.defaultTimeout)
    }


    def urlParams(): String = "?" + GetUpdates.OFFSET_FIELD + "=" + offset

    def buildValuePairs(): List[NameValuePair] = {
        List(
            new BasicNameValuePair(GetUpdates.OFFSET_FIELD, (offset + 1).toString),
            new BasicNameValuePair(GetUpdates.LIMIT_FIELD, limit + ""),
            new BasicNameValuePair(GetUpdates.TIMEOUT_FIELD, timeout + "")
        )
    }
}

object GetUpdates {

    val PATH = "getupdates"

    val OFFSET_FIELD = "offset"

    val defaultOffset = 0

    val LIMIT_FIELD = "limit"

    val defaultLimit = 100

    val TIMEOUT_FIELD = "timeout"

    val defaultTimeout = 20

    @volatile private var method = new GetUpdates(defaultOffset, GetUpdates.defaultLimit, GetUpdates.defaultTimeout)

    def urlParams(): String = method.urlParams

    def buildValuePairs(): List[NameValuePair] = method.buildValuePairs

    def buildValuePairs(offset: Int, limit: Int = method.limit, timeout: Int = method.timeout): List[NameValuePair] = {
        method = new GetUpdates(offset,limit, timeout)
        buildValuePairs
    }

}

