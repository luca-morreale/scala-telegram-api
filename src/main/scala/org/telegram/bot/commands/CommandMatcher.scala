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

import org.telegram.bot.api.Message
import scala.util.matching.Regex


/**
 * Class allowing to match commands.
 */

class CommandMatcher(botName: String, commandMap: Map[String, CommandCallback]) extends RegexCommand {

    def this(botName: String) {
        this(botName, Map())
    }

    /**
     * Sets the callback for the start command.
     */
    def setStartCommand(method: CommandCallback): CommandMatcher = {
        new CommandMatcher(botName, commandMap + ("/start" -> method))
    }

    /**
     * Sets the callback for the stop command.
     */
    def setStopCommand(method: CommandCallback): CommandMatcher = {
        new CommandMatcher(botName, commandMap + ("/stop" -> method))
    }

    /**
     * Sets the callback for the help command.
     */
    def setHelpCommand(method: CommandCallback): CommandMatcher = {
        new CommandMatcher(botName, commandMap + ("/help" -> method))
    }

    /**
     * Adds a command to the list of commands to be matched.
     *
     * @param command       command to be matched(the '/' will not be added)
     * @param callback      method to be called when the command has been matched
     */
    def addCommand(command: String, callback: CommandCallback): CommandMatcher = {
        new CommandMatcher(botName, commandMap + (command -> callback))
    }

    /**
     * Matchs the command inside the message text, and calls the callback.
     *
     * @param message   message received with an update
     * @throw UnknownCommandException if no command has been matched or the message
     *              does not contain any text.
     */
    def matchCommand(message: Message): Unit = {

        if(!message.text.isDefined) {
            throw new UnknownCommandException
        } else if(!commandMap.exists {
                        case(command, callback) =>
                            matchText(regex(command, botName), message, callback)
                        }){
            throw new UnknownCommandException("Unable to analyze " + message.text.get + " command")
        }
    }

    private def matchText(pattern: Regex, message: Message, callback: CommandCallback): Boolean = {
        val text = message.text.get
        text match {
            case pattern(_, params) =>
                callback(message, params.trim)
                true
            case _ => false
        }
    }
}

