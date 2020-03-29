package attributes

import Classification
import Consequence
import actions.definitions.Close
import actions.definitions.Open
import actions.ActionDetails

class Sealable(var state:State) : Attribute("sealable", Classification.STATE) {
    enum class State {
        OPEN, CLOSED
    }

    override fun toString(): String {
        return state.toString().toLowerCase()
    }

    override fun actOn(actionDetails: ActionDetails):Consequence? {
        when (actionDetails.action) {
            is Open -> {
                return Consequence {
                    this.state = State.OPEN
                    println("You open the ${actionDetails.subject.name}")
                }

            }
            is Close -> {
                return Consequence {
                    this.state = State.CLOSED
                    println("You close the ${actionDetails.subject.name}")
                }
            }
            else -> {
                return null
            }
        }
    }
}