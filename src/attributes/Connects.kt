package attributes

import Classification
import Consequence
import actions.ActionDetails
import actions.definitions.Enter
import world.Connection

class Connects(private var connection: Connection) : Attribute("connection",
    Classification.STATE
) {

    override fun description(): String? {
        return null
    }

    override fun actOn(actionDetails: ActionDetails):Consequence? {
        return when (actionDetails.action) {
            is Enter -> {
                Consequence {
                    if(it.world.currentArea == connection.area1) {
                        it.world.currentArea = connection.area2
                    } else {
                        it.world.currentArea = connection.area1
                    }
                    println("You go through the  ${actionDetails.subject.name}")
                }
            } else -> {
                null
            }
        }
    }
}