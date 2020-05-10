package attributes

import Classification
import Colour
import Consequence
import Thing
import actions.Action
import actions.ActionDetails
import actions.Verb
import actions.definitions.*

abstract class SuperContainer(val name:String, protected var contents:MutableList<Thing>) : Attribute(name,
    Classification.STATE
) {
    fun contents():MutableList<Thing> {
        return contents;
    }

}