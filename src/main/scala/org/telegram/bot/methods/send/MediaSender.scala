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

package org.telegram.bot.methods.send

import org.telegram.bot.methods.BaseMethod
import org.telegram.bot.methods.send.data.OutgoingData

/**
 * Extention to DataSender which is able to deliver media content, such as audio
 */

abstract class MediaSender[T <: OutgoingData](token: String, timeout: Int = BaseMethod.defaultTimeout)
                                                                            extends DataSender[T](token, timeout) {

     override def send(out: OutgoingData): Unit = {
        val multipart = out.buildMultipart.build
        sendData(multipart)
    }
}
