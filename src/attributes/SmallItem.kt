package attributes

import Classification
import Consequence
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
                    actionDetails.world.heldItems.add(actionDetails.subject)
                    actionDetails.world.currentArea.contains.remove(actionDetails.subject)

                    println("You take the ${actionDetails.subject.name}")
                }
            } else -> {
                null
            }
        }
    }
}