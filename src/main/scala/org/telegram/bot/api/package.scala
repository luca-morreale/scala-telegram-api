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

package org.telegram.bot

import scala.collection.mutable.HashMap
import scala.reflect.runtime.currentMirror
import scala.reflect.runtime.universe.TypeTag
import scala.reflect.runtime.universe.MethodMirror
import scala.reflect.runtime.universe.ClassSymbol
import scala.reflect.runtime.universe.typeOf
import scala.reflect.runtime.universe.nme
import scala.reflect.runtime.universe.runtimeMirror

import org.json4s.DefaultFormats
import org.json4s.JField
import org.json4s.JValue

/**
 *
 */

package object api {

    /**
     *  Brings in default formats for parsing json
     */
    implicit val formats = DefaultFormats

    /**
     * Runtime mirror class loader
     */
    val mirror = runtimeMirror(getClass.getClassLoader)

    /**
     * A mutable HashMap which stores names of classes associated
     * with its MethodMirror, that allows to instantiate the object.
     * Used to avoid continuous interaction with reflection.
     */
    val classMap = HashMap[String, MethodMirror]()

    /**
     * Checks whether exits a field in the json value
     */
    def fieldExists(json: JValue, field: String): Boolean = {
        val value = json findField {
                    case JField(field, _) => true
                    case _ => false
                }
        value != Option(None)
    }

    /**
     *
     *
     */
    def extractAPI[API: Manifest](json: JValue, field: String): Option[API] = {
        if (fieldExists(json, field))
            Some((json \ field).extract[API])
        else
            None
    }

    /**
     * Dynamically parse the json creating the given class using reflection package.
     */
    def apiFromJson[API: TypeTag](json: JValue, field: String): Option[API] = {

        if(fieldExists(json, field)) {
            val generator = extractGenerator[API]()
            Some(generator(json \ field).asInstanceOf[API])
        } else {
            None
        }
    }

    /**
     * Extracts the generator from the HashMap or creates it, in case it does not yet exits.
     */
    private def extractGenerator[API: TypeTag](): MethodMirror = {

        val apiClass = typeOf[API].typeSymbol.asClass
        val name = apiClass.fullName

        val generator = classMap get name
        if(generator != None) {
            generator.get
        } else {

            val gen = createGenerator[API](apiClass)
            classMap.put(name, gen)
            gen
        }
    }

    private def createGenerator[API: TypeTag](apiClass: ClassSymbol): MethodMirror = {
        val classMirror = mirror.reflectClass(apiClass)
        val constructor = typeOf[API].declaration(nme.CONSTRUCTOR).asMethod

        classMirror.reflectConstructor(constructor)
    }

}
