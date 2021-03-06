package attributes

import Classification
import Consequence
import Thing
import actions.definitions.Close
import actions.definitions.Open
import actions.ActionDetails
import actions.definitions.Enter
import actions.definitions.Look

class Sealable(var state:State) : Attribute("sealable", Classification.STATE) {
    enum class State {
        OPEN, CLOSED
    }

    override fun description(): String {
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

    override fun intercepts(actionDetails:ActionDetails):Consequence? {
        return when(actionDetails.action) {
            is Enter -> {
                if (state == State.CLOSED) {
                    return Consequence {
                        println("The ${actionDetails.subject.name} is closed")
                    }
                } else {
                    return null
                }
            }
            is Look -> {
                if(state == State.CLOSED) {
                    return Consequence {
                        println("The ${actionDetails.subject.name} is closed")
                    }
                } else {
                    return null
                }
            } else -> {
                null
            }
        }
    }

    override fun interceptsContents(container:Thing, contentsType:SuperContainer):Boolean {
        return when(contentsType) {
            is Contains -> {
                return state == State.CLOSED
            } else -> {
                false
            }
        }
    }

}