package actions

import Consequence
import Thing

open class Action(val name: ActionName) {
    fun apply(actionDetails: ActionDetails):Consequence? {
        for(att in actionDetails.subject.attributes) {
            val consequence:Consequence? = att.intercepts(actionDetails)
            if(consequence != null) {
                return consequence
            }
        }
        for(att in actionDetails.subject.attributes) {
            val consequence:Consequence? = att.actOn(actionDetails)
            if(consequence != null) {
                return consequence
            }
        }
        return null
    }
}