package attributes

import Consequence
import Thing
import Verb
import actions.Picklock
import actions.Unlock

class Locked(private val check: (Locked, Thing) -> Boolean): Attribute("locked", Classification.STATE) {

    override fun actOn(verb: Verb, owner: Thing):Consequence? {
        when (verb.action) {
            is Unlock -> {
                if(check.invoke(this, owner)) {
                    return Consequence {
                        owner.attributes.remove(this)
                        println("You unlock the ${owner.name}")
                    }
                } else {
                    return Consequence {
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
}