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
import org.telegram.bot.TelegramInformation
import org.telegram.bot.methods.BaseMethod
import org.telegram.bot.methods.receive.getupdate.UpdateProducer
import org.telegram.bot.methods.receive.getupdate.UpdateConsumer

/**
 *
 */

class GetUpdates(token: String, initialOffset: Int = 0, timeout: Int = 20) extends BaseMethod(token) {

    override def path(): String = "getupdates"

    private val producer = new UpdateProducer(token, initialOffset, timeout)
    private val consumer = new UpdateConsumer

    val system = producer ==> consumer

    def start(): Unit = system.start

    def addCallback(callback: Update => Unit):Unit = consumer.addCallback(callback)

    def removeCallback(callback: Update => Unit):Unit = consumer.removeCallback(callback)

}