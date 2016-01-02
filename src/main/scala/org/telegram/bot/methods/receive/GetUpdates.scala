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

import org.telegram.bot.api.Update
import org.telegram.bot.methods.BaseMethod
import org.telegram.bot.methods.receive.getupdate.UpdateProducer
import org.telegram.bot.methods.receive.getupdate.UpdateConsumer

/**
 * Class which provides a constant and complte interaction
 * with telegram to download Updates, using getupdates method.
 */

class GetUpdates(token: String, initialOffset: Int = 0, timeout: Int=BaseMethod.defaultTimeout) {

    private val producer = new UpdateProducer(token, initialOffset, timeout)

    private val consumer = new UpdateConsumer

    /**
     * Reference to the class which is the core of this class
     */
    val system = producer ==> consumer

    /**
     * Starts to download the updates
     */
    def start(): Unit = system.start

    /**
     * Adds a callback to the list, each callback will be called
     * after parsing a new update
     */
    def addCallback(callback: Update => Unit):Unit = consumer.addCallback(callback)

    /**
     * Removes the callback
     */
    def removeCallback(callback: Update => Unit):Unit = consumer.removeCallback(callback)

}
