package attributes

import Classification
import Consequence
import Parent
import actions.Action
import actions.ActionDetails
import actions.definitions.Paint
import actions.definitions.Pickup
import actions.definitions.Shoot

class Shoots(var ammo:Int) : Attribute("small",
    Classification.STATE
) {

    override fun description(): String? {
        return null
    }

    override fun grantActions(): List<Action> {
        return listOf(Shoot(this))
    }

}