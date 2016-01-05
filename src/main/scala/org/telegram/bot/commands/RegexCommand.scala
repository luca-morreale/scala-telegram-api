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

package org.telegram.bot.commands

import scala.util.matching.Regex

/**
 * This trait contains the basic methods which allows to create
 * regular expression pattern for the commands.
 */

trait RegexCommand {

    /**
     * Returns the regex to match words after the command.
     */
    protected def wordPattern = "(|\\s[\\s\\w]*)"

    /**
     * Returns the regex to match the command, with or without.
     * the bot's name
     */
    protected def botNameRegex(botName: String): String = "(|@" + botName + ")"

    /**
     * Returns the regex compiled  to match commands.
     */
    protected def regex(command: String, botName: String): Regex = (command + botNameRegex(botName) + wordPattern).r

}
