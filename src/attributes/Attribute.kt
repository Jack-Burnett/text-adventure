package attributes

import Classification
import Consequence
import Thing
import Verb

abstract class Attribute(
    private val name: String,
    val classification: Classification
) {

    override fun toString(): String {
        return name
    }

    open fun actOn(verb: Verb, owner: Thing):Consequence? { return null}

    open fun intercepts(verb: Verb, owner: Thing):Consequence? { return null}
}