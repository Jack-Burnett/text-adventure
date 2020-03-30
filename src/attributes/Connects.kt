package attributes

import Classification
import Colour
import Consequence
import actions.definitions.Paint
import Thing
import actions.Action
import actions.ActionDetails
import actions.Verb
import actions.definitions.Enter
import areas.Connection

class Connects(private var connection: Connection) : Attribute("connection",
    Classification.STATE
) {

    override fun toString(): String {
        return ""
    }

    override fun actOn(actionDetails: ActionDetails):Consequence? {
        return when (actionDetails.action) {
            is Enter -> {
                Consequence {
                    if(actionDetails.core.world.currentArea == connection.area1) {
                        actionDetails.core.world.currentArea = connection.area2
                    } else {
                        actionDetails.core.world.currentArea = connection.area1
                    }
                    println("You go through the  ${actionDetails.subject.name}")
                }
            } else -> {
                null
            }
        }
    }
}