package actions

import Consequence
import Thing

open class Action(val name: ActionName) {
    fun apply(action: Action, subject: Thing, verb: Verb):Consequence? {
        for(att in subject.attributes) {
            val consequence:Consequence? = att.intercepts(action, verb, subject)
            if(consequence != null) {
                return consequence
            }
        }
        for(att in subject.attributes) {
            val consequence:Consequence? = att.actOn(action, verb, subject)
            if(consequence != null) {
                return consequence
            }
        }
        return null
    }
}