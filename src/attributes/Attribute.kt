package attributes

import Classification
import Consequence
import Thing
import actions.Action
import actions.ActionDetails
import actions.Verb
import actions.definitions.Paint

abstract class Attribute(
    private val name: String,
    val classification: Classification
) {

    open fun description(): String? {
        return name
    }

    open fun actOn(details: ActionDetails):Consequence? { return null}

    open fun intercepts(details:ActionDetails):Consequence? { return null}

    open fun interceptsContents(details:ActionDetails):Consequence? { return null}

    open fun grantActions(): List<Action> {
        return listOf()
    }

}