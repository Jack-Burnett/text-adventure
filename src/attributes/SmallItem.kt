package attributes

import Classification
import Colour
import Consequence
import actions.definitions.Paint
import Thing
import actions.Action
import actions.ActionDetails
import actions.Verb
import actions.definitions.Pickup

class SmallItem() : Attribute("small",
    Classification.SIZE
) {

    override fun toString(): String {
        return "small"
    }

    override fun actOn(actionDetails: ActionDetails):Consequence? {
        return when (actionDetails.action) {
            is Pickup -> {
                Consequence {
                    actionDetails.core.heldItems.add(actionDetails.subject)
                    actionDetails.core.context.remove(actionDetails.subject)

                    println("You take the ${actionDetails.subject.name}")
                }
            } else -> {
                null
            }
        }
    }
}