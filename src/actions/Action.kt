package actions

import Consequence
import Thing
import Verb
import org.w3c.dom.Attr
import javax.management.Attribute

open class Action(canonicalName: String) {
    val canonicalName = canonicalName
    fun apply(subject: Thing, verb: Verb):Consequence? {
        for(att in subject.attributes) {
            val consequence:Consequence? = att.actOn(verb, subject);
            if(consequence != null) {
                return consequence
            }
        }
        return null
    }
}