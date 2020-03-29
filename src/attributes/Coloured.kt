package attributes

import Classification
import Colour
import Consequence
import actions.definitions.Paint
import Thing
import actions.Action
import actions.Verb

class Coloured(private var state: Colour) : Attribute("colour",
    Classification.COLOUR
) {

    override fun toString(): String {
        return state.toString().toLowerCase()
    }

    override fun actOn(action: Action, verb: Verb, owner: Thing):Consequence? {
        return when (action) {
            is Paint -> {
                Consequence {
                    this.state = action.colour
                    println("You paint the ${owner.name} ${this.state.toString().toLowerCase()}")
                }
            } else -> {
                null
            }
        }
    }
}