package attributes

import Classification
import Thing
import Verb

open abstract class Attribute(
    private val name: String,
    val classification: Classification
) {

    override fun toString(): String {
        return name
    }

    open fun actOn(verb: Verb, owner: Thing){}
}