package attributes

import Classification
import Consequence
import actions.definitions.Close
import actions.definitions.Open
import Thing
import actions.Action
import actions.Verb

class Sealable(var state:State) : Attribute("sealable", Classification.STATE) {
    enum class State {
        OPEN, CLOSED
    }

    override fun toString(): String {
        return state.toString().toLowerCase()
    }

    override fun actOn(action: Action, verb: Verb, owner: Thing):Consequence? {
        when (action) {
            is Open -> {
                return Consequence {
                    this.state = State.OPEN
                    println("You open the ${owner.name}")
                }

            }
            is Close -> {
                return Consequence {
                    this.state = State.CLOSED
                    println("You close the ${owner.name}")
                }
            }
            else -> {
                return null
            }
        }
    }
}