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

package org.telegram.bot.api

/**
 *
 */

case class Contact (
        val phone_number: String,
        val first_name: String,
        val last_name: Option[String],
        val user_id: Option[Int]
                   ) extends Ordered[Contact] with APIClass {

    def compare(that: Contact):Int = phone_number compareTo that.phone_number

    override def toString(): String = "Contact [phone_number: " + phone_number + ", first_name: " + first_name +
                                    ", last_name: " + last_name + "user_id: " + user_id + "]"

}
