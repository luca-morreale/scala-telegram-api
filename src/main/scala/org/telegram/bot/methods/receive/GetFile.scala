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

import org.telegram.bot.methods.buildValuePairs
import org.telegram.bot.methods.generateHttpPost
import org.telegram.bot.methods.BaseMethod
import org.telegram.bot.util.BotLogger
import org.telegram.bot.api.File

import scala.collection.immutable.HashMap

/**
 *
 */

class GetFile(token: String) extends BaseMethod(token) {

    private val log = BotLogger.getLogger(classOf[GetFile].getName)

    override def path(): String = "getfile"

    private val url = super.path + token + "/" + path

    def request(file_id: Int): Option[File] = {
        val pairs = buildValuePairs(HashMap("file_id" -> file_id.toString))
        val httpPost = generateHttpPost(url)

        handleAnswer[File](httpClient, httpPost)
    }
}
