package attributes

import Classification
import Consequence
import Thing
import actions.Action
import actions.ActionDetails
import actions.Verb

abstract class Attribute(
    private val name: String,
    val classification: Classification
) {

    open fun description(): String? {
        return name
    }

    open fun actOn(details: ActionDetails):Consequence? { return null}

    open fun intercepts(details:ActionDetails):Consequence? { return null}

}


maybe attributes can mask other attributes entirely instead of just blocking them...
        but the error messages would be bad?