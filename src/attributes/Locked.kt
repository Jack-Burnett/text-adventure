package attributes

import Consequence
import Thing
import actions.Action
import actions.ActionDetails
import actions.Verb
import actions.definitions.Open
import actions.definitions.Picklock
import actions.definitions.Unlock

class Locked(private val check: (Locked, Thing) -> Boolean): Attribute("locked", Classification.STATE) {

    override fun actOn(actionDetails:ActionDetails):Consequence? {
        when (actionDetails.action) {
            is Unlock -> {
                return if(check.invoke(this, actionDetails.subject)) {
                    Consequence {
                        actionDetails.subject.attributes.remove(this)
                        println("You unlock the ${actionDetails.subject.name}")
                    }
                } else {
                    Consequence {
                        println("You cannot open the lock")
                    }
                }
            }
            is Picklock -> {
                return Consequence {
                    actionDetails.subject.attributes.remove(this)
                    println("You pick the lock on the ${actionDetails.subject.name}")
                }
            }
            else -> {
                return null
            }
        }
    }

    override fun intercepts(actionDetails:ActionDetails):Consequence? {
        return when(actionDetails.action) {
            is Open -> {
                Consequence {
                    println("The door is locked")
                }
            } else -> {
                null
            }
        }
    }
}