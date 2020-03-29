package attributes

import Thing
import Verb
import actions.Picklock
import actions.Unlock

class Locked(private val check: (Locked, Thing) -> Boolean): Attribute("locked", Classification.STATE) {

    override fun actOn(verb: Verb, owner: Thing) {
        when (verb.action) {
            is Unlock -> {
                if(check.invoke(this, owner)) {
                    owner.attributes.remove(this)
                    println("You unlock the ${owner.name}")
                } else {
                    println("You cannot open the lock")
                }
            }
            is Picklock -> {
                owner.attributes.remove(this)
                println("You pick the lock on the ${owner.name}")
            }
        }
    }
}