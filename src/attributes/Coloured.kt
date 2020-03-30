package attributes

import Classification
import Colour
import Consequence
import actions.definitions.Paint
import Thing
import actions.Action
import actions.ActionDetails
import actions.Verb

class Coloured(private var state: Colour) : Attribute("colour",
    Classification.COLOUR
) {

    override fun description(): String {
        return state.toString().toLowerCase()
    }

    override fun actOn(actionDetails: ActionDetails):Consequence? {
        return when (actionDetails.action) {
            is Paint -> {
                Consequence {
                    this.state = actionDetails.action.colour
                    println("You paint the ${actionDetails.subject.name} ${this.state.toString().toLowerCase()}")
                }
            } else -> {
                null
            }
        }
    }
}