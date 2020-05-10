package actions

import Consequence

open class Action(val name: ActionName) {
    fun apply(actionDetails: ActionDetails):Consequence? {
        val consequence = this.intercept(actionDetails)
        if(consequence != null) {
            return consequence
        }
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

    open fun intercept(actionDetails: ActionDetails):Consequence? {
        return null
    }
}