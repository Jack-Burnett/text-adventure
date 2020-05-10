package attributes

import Classification
import Consequence
import Thing
import actions.Action
import actions.ActionDetails

abstract class Attribute(
    private val name: String,
    val classification: Classification
) {

    open fun description(): String? {
        return name
    }

    open fun actOn(actionDetails: ActionDetails):Consequence? { return null}

    open fun intercepts(actionDetails:ActionDetails):Consequence? { return null}

    open fun  interceptsContents(container:Thing, contentsType:SuperContainer):Boolean { return false}

    open fun grantActions(): List<Action> {
        return listOf()
    }

}