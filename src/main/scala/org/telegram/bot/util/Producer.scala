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

package org.telegram.bot.util

import java.util.concurrent.LinkedBlockingQueue

/**
 *
 */

trait Producer[T] extends Coroutine {

    private val capacity = 100
    private val outputs = new LinkedBlockingQueue[T](capacity)

    protected def put(output: T): Unit = {
        outputs put output
    }

    def next(): T = outputs.take

    def ==>(consumer: Consumer[T]): Coroutine = {
        val that = this

        new Coroutine {
            override def run():Unit = {
                while (true) {
                    val message = that.next
                    consumer accept message
                }
            }

            override def start(): Unit = {
                that.start
                consumer.start
                super.start
            }
        }
    }

}
