package attributes

import Classification
import Colour
import Consequence
import actions.definitions.Paint
import Thing
import actions.Action
import actions.ActionDetails
import actions.Verb

class Contains(public var contents:MutableList<Thing>) : Attribute("contains",
    Classification.STATE
) {

    override fun toString(): String {
        return "full"
    }

    // need someone to broadcast extra things to the context?
}