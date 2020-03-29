package attributes

import Consequence
import Thing
import actions.Action
import actions.Verb
import actions.definitions.Open
import actions.definitions.Picklock
import actions.definitions.Unlock

class Locked(private val check: (Locked, Thing) -> Boolean): Attribute("locked", Classification.STATE) {

    override fun actOn(action: Action, verb: Verb, owner: Thing):Consequence? {
        when (action) {
            is Unlock -> {
                return if(check.invoke(this, owner)) {
                    Consequence {
                        owner.attributes.remove(this)
                        println("You unlock the ${owner.name}")
                    }
                } else {
                    Consequence {
                        println("You cannot open the lock")
                    }
                }
            }
            is Picklock -> {
                return Consequence {
                    owner.attributes.remove(this)
                    println("You pick the lock on the ${owner.name}")
                }
            }
            else -> {
                return null
            }
        }
    }

    override fun intercepts(action: Action, verb: Verb, owner: Thing):Consequence? {
        return when(action) {
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