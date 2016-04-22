package org.telegram.bot.methods.receive

/**
 * Created by luca on 22/04/16.
 */
class NoneUserException(msg: String) extends  RuntimeException(msg) {

    def this() = {
        this("The response do not contain a user, it is possible that the token is wrong or the bot is not registered to telegram")
    }

}
