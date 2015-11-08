package org.telegram.bot.methods.receive

import org.apache.http.NameValuePair
import org.apache.http.message.BasicNameValuePair

/**
 *
 */

package object getupdate {

    val PATH = "getupdates"

    val OFFSET_FIELD = "offset"

    val defaultOffset = 0

    val LIMIT_FIELD = "limit"

    val defaultLimit = 100

    val TIMEOUT_FIELD = "timeout"

    val defaultTimeout = 20

    @volatile private var method = new UpdateCounter(defaultOffset, defaultLimit, defaultTimeout)

    def urlParams(): String = method.urlParams

    def buildValuePairs(): List[NameValuePair] = method.buildValuePairs

    def buildValuePairs(offset: Int, limit: Int = method.limit, timeout: Int = method.timeout): List[NameValuePair] = {
        method = new UpdateCounter(offset,limit, timeout)
        buildValuePairs
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

    def buildValuePairs(): List[NameValuePair] = {
        List(
            new BasicNameValuePair(getupdate.OFFSET_FIELD, (offset + 1).toString),
            new BasicNameValuePair(getupdate.LIMIT_FIELD, limit + ""),
            new BasicNameValuePair(getupdate.TIMEOUT_FIELD, timeout + "")
        )
    }
}