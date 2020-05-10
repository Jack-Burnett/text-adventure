package attributes

import Classification
import Consequence
import Parent
import actions.ActionDetails
import actions.definitions.Pickup

class SmallItem() : Attribute("small",
    Classification.SIZE
) {

    override fun description(): String {
        return "small"
    }

    override fun actOn(actionDetails: ActionDetails):Consequence? {
        return when (actionDetails.action) {
            is Pickup -> {
                Consequence {
                    val parent:Parent? = actionDetails.subject.getParent()
                    if (parent != null) {
                        parent.removeChild(actionDetails.subject)
                        it.world.heldItems.add(actionDetails.subject)
                        println("You take the ${actionDetails.subject.name}")
                    } else {
                        println("UNABLE TO TAKE ITEM")
                    }

                }
            } else -> {
                null
            }
        }
    }
}