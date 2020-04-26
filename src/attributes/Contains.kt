package attributes

import Classification
import Colour
import Consequence
import actions.definitions.Paint
import Thing
import actions.Action
import actions.ActionDetails
import actions.Verb

class Contains(private var contents:MutableList<Thing>) : Attribute("contains",
    Classification.STATE
) {

    override fun toString(): String {
        return "full"
    }

    public fun contents():MutableList<Thing> {
        return contents;
    }

    // need someone to broadcast extra things to the context?
}