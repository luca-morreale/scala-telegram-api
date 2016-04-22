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

import org.telegram.bot.api.User

/**
 * Class which provides the getme method.
 */

class GetMe(token: String, timeout: Int, name: String) extends BaseGetMethod(token) {

    override def url(): String = super.url + token + "/" + "getme"

    /**
     * Sends a request to telegram's getme method.
     *
     * @return          in case of positive answer returns an API User class
     */
    def getMe(): Option[User] = request[User]

    /**
     * Checks the token given and the one received form telegram
     * are the same.
     */
    def checkToken(): Boolean = {
        val userOpt = getMe
        if(userOpt == None) {
            throw new NoneUserException
        }else {
            userOpt.get.id.toString == token.split(":")(0) &&
                userOpt.get.first_name == name
        }
    }
}
