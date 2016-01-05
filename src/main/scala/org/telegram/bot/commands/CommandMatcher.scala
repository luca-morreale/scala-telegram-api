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
 *
 */

class CommandMatcher(botName: String, commandMap: Map[String, CommandCallback]) extends RegexCommand {

    def this(botName: String) {
        this(botName, Map())
    }

    def setStartCommand(method: CommandCallback): CommandMatcher = {
        new CommandMatcher(botName, commandMap + ("/start" -> method))
    }

    def setStopCommand(method: CommandCallback): CommandMatcher = {
        new CommandMatcher(botName, commandMap + ("/stop" -> method))
    }

    def setHelpCommand(method: CommandCallback): CommandMatcher = {
        new CommandMatcher(botName, commandMap + ("/help" -> method))
    }

    def addCommand(command: String, callback: CommandCallback): CommandMatcher = {
        new CommandMatcher(botName, commandMap + (command -> callback))
    }

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

    protected def matchText(pattern: Regex, message: Message, callback: CommandCallback): Boolean = {
        val text = message.text.get
        text match {
            case pattern(_, params) =>
                callback(message, params.trim)
                true
            case _ => false
        }
    }
}

