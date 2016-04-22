package org.telegram.bot.methods.receive

import org.telegram.bot.methods.MethodDebugger
import org.telegram.bot.methods.pairsToEntity
import org.telegram.bot.methods.generateHttpPost

import org.apache.http.NameValuePair

/**
 * Created by luca on 22/04/16.
 */
abstract class BaseGetMethod(token: String) extends DataReceiver(token) with MethodDebugger {

    /**
     *  Builds the request and sends it to the actual url of the Get method
     *
     *  @param pairs        pairs to be included in the request
     *  @return             option of the specified API classs extracted from the response
     */
    def request[T >: Null: Manifest](pairs: List[NameValuePair]): Option[T] = {

        val entity = pairsToEntity(pairs)
        val httpPost = generateHttpPost(url, entity)

        debug(httpPost, entity)

        handleAnswer[T](httpClient, httpPost)
    }

    /**
     *  Builds the request and sends it to the actual url of the Get method
     *
     *  @return             option of the specified API classs extracted from the response
     */
    def request[T >: Null: Manifest](): Option[T] = {

        val httpPost = generateHttpPost(url)

        debug(httpPost)

        handleAnswer[T](httpClient, httpPost)
    }

}
