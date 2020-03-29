package attributes

import Classification
import Colour
import Consequence
import actions.definitions.Paint
import Thing
import actions.Action
import actions.ActionDetails
import actions.Verb

class Colours(private var state: Colour) : Attribute("colour",
    Classification.COLOUR
) {

    override fun toString(): String {
        return state.toString().toLowerCase()
    }

    override fun grantActions(): List<Action> {
        return listOf(Paint(state))
    }
}