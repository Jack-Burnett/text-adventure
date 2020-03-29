package attributes

import Classification
import Consequence
import Thing
import actions.Action
import actions.Verb

abstract class Attribute(
    private val name: String,
    val classification: Classification
) {

    override fun toString(): String {
        return name
    }

    open fun actOn(action: Action, verb: Verb, owner: Thing):Consequence? { return null}

    open fun intercepts(action:Action, verb: Verb, owner: Thing):Consequence? { return null}
}