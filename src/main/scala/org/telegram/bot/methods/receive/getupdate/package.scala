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

package org.telegram.bot.methods.receive

import org.telegram.bot.methods.buildValuePairs

import org.apache.http.NameValuePair
import org.apache.http.message.BasicNameValuePair

import scala.collection.immutable.HashMap

/**
 *
 */

package object getupdate {

    val OFFSET_FIELD = "offset"

    val defaultOffset = 0

    val LIMIT_FIELD = "limit"

    val defaultLimit = 100

    val TIMEOUT_FIELD = "timeout"

    val defaultTimeout = 20

    @volatile private var counter = new UpdateCounter(defaultOffset, defaultLimit, defaultTimeout)

    def urlParams(): String = counter.urlParams

    def generateUpdatePairs(): List[NameValuePair] = {
        buildValuePairs(HashMap(
                getupdate.OFFSET_FIELD -> (counter.offset + 1).toString,
                getupdate.LIMIT_FIELD -> counter.limit.toString,
                getupdate.TIMEOUT_FIELD -> counter.timeout.toString
            ))
    }

    def generateUpdatePairs(offset: Int, limit: Int = counter.limit, timeout: Int = counter.timeout): List[NameValuePair] = {
        counter = new UpdateCounter(offset,limit, timeout)
        generateUpdatePairs
    }

}

private class UpdateCounter(
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
        this(getupdate.defaultOffset,
                getupdate.defaultLimit,
                getupdate.defaultTimeout)
    }

    def urlParams(): String = "?" + getupdate.OFFSET_FIELD + "=" + offset
}
