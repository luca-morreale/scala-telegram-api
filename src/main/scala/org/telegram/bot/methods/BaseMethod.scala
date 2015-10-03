package org.telegram.bot.methods

/**
 * @author luca
 */
abstract class BaseMethod(token: String) {

    protected def token(): String = token

}