package attributes

import Classification
import actions.Close
import actions.Open
import Thing
import Verb

class Sealable(var state:State) : Attribute("sealable", Classification.STATE) {
    enum class State {
        OPEN, CLOSED
    }

    override fun toString(): String {
        return state.toString().toLowerCase()
    }

    override fun actOn(verb: Verb, owner: Thing) {
        when (verb.action) {
            is Open -> {
                this.state = State.OPEN;
                println("You open the ${owner.name}")
            }
            is Close -> {
                this.state = State.CLOSED;
                println("You close the ${owner.name}")
            }
        }
    }
}