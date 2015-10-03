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

import org.telegram.bot.util.Consumer
import org.telegram.bot.api.Update

/**
 *
 */

class UpdateConsumer extends Consumer[Update] {

    var callbacks: List[Update => Unit] = Nil

    def run(): Unit = {
        while(true) {

            val update = this.get
            callbacks.foreach(callback => callback(update))
        }
    }

    def addCallback(callback: Update => Unit):Unit = callbacks ::= callback

    def removeCallback(callback: Update => Unit):Unit = callbacks = callbacks filterNot callback.eq

}
