package actions

import Thing
import Verb
import org.w3c.dom.Attr
import javax.management.Attribute

open class Action(canonicalName: String) {
    val canonicalName = canonicalName
    fun apply(subject: Thing, verb: Verb) {
        for(att in subject.attributes) {
            att.actOn(verb, subject);
        }
    }
}