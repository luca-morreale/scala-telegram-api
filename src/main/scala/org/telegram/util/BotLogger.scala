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

package org.telegram.util

import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.io.PrintWriter
import java.util.Calendar
import java.util.GregorianCalendar
import java.util.concurrent.ConcurrentHashMap
import java.util.logging.ConsoleHandler
import java.util.logging.Level
import java.util.logging.Logger

/**
 * Custom logger class
 */

protected class BotLogger(className: String) extends Logger(className, null) {

    private val loggingFile = createLoggingFile

    private val logsConsumer = new LoggerThread
    logsConsumer.start

    setLevel(Level.ALL)
    addHandler(new ConsoleHandler)
    getHandlers.foreach { _.setLevel(Level.ALL) }

    private def createLoggingFile(): PrintWriter = {
        val fileName = "./" + BotLogger.dateFormaterForFileName + ".log"

        try {
            val file = new File(fileName)
            if (!file.exists()) {
                file.createNewFile
            }
            new PrintWriter(new BufferedWriter(new FileWriter(file)))
        } catch {
            case io: IOException =>
                io.printStackTrace
                null
        }
    }

    /**
     * ===============================================================================
     *
     * Public methods to log information
     *
     * ===============================================================================
     */

    override def log(level: Level, msg: String):Unit = {
        super.log(level, msg)
        logToFile(level, msg)
    }

    override def severe(msg: String):Unit = {
        super.severe(msg)
        logToFile(Level.SEVERE, msg)
    }

    def debug(msg: String):Unit = {
        fine(msg)
    }

    def error(msg: String):Unit = {
        severe(msg)
    }

    def trace(msg: String):Unit = {
        finer(msg)
    }

    override def warning(msg: String):Unit = {
        super.warning(msg)
        logToFile(Level.WARNING, msg)
    }

    override def info(msg: String):Unit = {
        super.info(msg)
        logToFile(Level.INFO, msg)
    }

    override def config(msg: String):Unit = {
        super.config(msg)
        logToFile(Level.CONFIG, msg)
    }

    override def fine(msg: String):Unit = {
        super.fine(msg)
        logToFile(Level.FINE, msg)
    }

    override def finer(msg: String):Unit = {
        super.finer(msg)
        logToFile(Level.FINER, msg)
    }

    override def finest(msg: String):Unit = {
        super.finest(msg)
        logToFile(Level.FINEST, msg)
    }

    def log(level: Level, throwable: Throwable):Unit = {
        throwable.printStackTrace()
        logToFile(level, throwable)
    }

    override def log(level: Level, msg: String, throwable: Throwable):Unit = {
        super.log(level, msg, throwable)
        logToFile(level, msg)
        logToFile(level, throwable)
    }

    def severe(msg: String, throwable: Throwable):Unit = {
        log(Level.SEVERE, msg, throwable)
    }

    def warning(msg: String, throwable: Throwable):Unit = {
        log(Level.WARNING, msg, throwable)
    }

    def info(msg: String, throwable: Throwable):Unit = {
        log(Level.INFO, msg, throwable)
    }

    def config(msg: String, throwable: Throwable):Unit = {
        log(Level.CONFIG, msg, throwable)
    }

    def fine(msg: String, throwable: Throwable):Unit = {
        log(Level.FINE, msg, throwable)
    }

    def finer(msg: String, throwable: Throwable):Unit = {
        log(Level.FINER, msg, throwable)
    }

    def finest(msg: String, throwable: Throwable):Unit = {
        log(Level.FINEST, msg, throwable)
    }

    def debug(msg: String, throwable: Throwable):Unit = {
        log(Level.FINE, msg, throwable)
    }

    def severe(throwable: Throwable):Unit = {
        logToFile(Level.SEVERE, throwable)
    }

    def warning(throwable: Throwable):Unit = {
        logToFile(Level.WARNING, throwable)
    }

    def info(throwable: Throwable):Unit = {
        logToFile(Level.INFO, throwable)
    }

    def config(throwable: Throwable):Unit = {
        logToFile(Level.CONFIG, throwable)
    }

    def fine(throwable: Throwable):Unit = {
        logToFile(Level.FINE, throwable)
    }

    def finer(throwable: Throwable):Unit = {
        logToFile(Level.FINER, throwable)
    }

    def finest(throwable: Throwable):Unit = {
        logToFile(Level.FINEST, throwable)
    }

    def warn(throwable: Throwable):Unit = {
        warning(throwable)
    }

    def debug(throwable: Throwable):Unit = {
        fine(throwable)
    }

    def error(throwable: Throwable):Unit = {
        severe(throwable)
    }

    def trace(throwable: Throwable):Unit = {
        finer(throwable)
    }

    /**
     * ============================================================================
     *
     * End logger public methods
     *
     * =============================================================================
     */

    private def logToFile(level: Level, throwable: Throwable) = {
        if (isLoggable(level)) {
            val dateForLog = BotLogger.dateFormaterForLogs
            logThrowableToFile(level, throwable, dateForLog)
        }
    }

    private def logToFile(level: Level, msg: String) = {
        if (isLoggable(level)) {
            val dateForLog = BotLogger.dateFormaterForLogs
            logMsgToFile(level, msg, dateForLog)
        }
    }

    private def logMsgToFile(level: Level, msg: String, dateForLog: String) = {
        val log = dateForLog + " [" + super.getName + "]" + level.toString() + " - " + msg
        logsConsumer accept log
    }

    private def logThrowableToFile(level: Level, throwable: Throwable, dateForLog: String) = {

        val elementList = for {
            element <- throwable.getStackTrace
        } yield {
            "\tat " + element + "\n"
        }
        val throwableLog = dateForLog + level.getName + " - " + throwable + "\n" + elementList mkString("")

        logsConsumer accept throwableLog
    }

    /**
     * Coroutine used to write over a file.
     */
    private class LoggerThread extends Consumer[String] {

        override def run():Unit = {
            while (true) {
                val log = this.get
                loggingFile.synchronized {
                    loggingFile.println(log)
                    loggingFile.flush
                }
            }
        }
    }

    override protected def finalize() = {
        try {
            loggingFile.synchronized{
                loggingFile.flush
                loggingFile.close
            }
            super.finalize
        } catch {
            case e: Exception =>
        }
    }

}

object BotLogger {

    private val instances = new ConcurrentHashMap[String, BotLogger]

    def getLogger(className: String):BotLogger = {

        this.synchronized {
            if (!instances.containsKey(className)) {
                val instance = new BotLogger(className)
                instances.put(className, instance)
            }
        }
        instances get className
    }

    protected def dateFormaterForLogs(): String = {
        val calendar = new GregorianCalendar
        val sep = "_"
        "[" + calendar.get(Calendar.DAY_OF_MONTH) + sep +
            (calendar.get(Calendar.MONTH) + 1) + sep +
            calendar.get(Calendar.YEAR) + sep +
            calendar.get(Calendar.HOUR_OF_DAY) + sep +
            calendar.get(Calendar.MINUTE) + ":" +
            calendar.get(Calendar.SECOND) + "] "
    }

    protected def dateFormaterForFileName(): String = {
        val calendar = new GregorianCalendar
        calendar.get(Calendar.DAY_OF_MONTH).toString() +
            (calendar.get(Calendar.MONTH) + 1) +
            calendar.get(Calendar.YEAR)
    }

}

