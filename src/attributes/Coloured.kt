package attributes

import Classification
import Colour
import Consequence
import actions.Paint
import Thing
import Verb

class Coloured(private var state: Colour) : Attribute("colour",
    Classification.COLOUR
) {

    override fun toString(): String {
        return state.toString().toLowerCase()
    }

    override fun actOn(verb: Verb, owner: Thing):Consequence? {
        return when (verb.action) {
            is Paint -> {
                Consequence {
                    this.state = verb.action.colour;
                    println("You paint the ${owner.name} ${this.state.toString().toLowerCase()}")
                }
            } else -> {
                null
            }
        }
    }
}