package actions.definitions

import Consequence
import actions.Action
import actions.ActionDetails
import actions.ActionName
import attributes.Shoots

class Shoot(private val granter: Shoots) : Action(ActionName.SHOOT) {
    fun decrementAmmo() {
        granter.ammo--
    }

    override fun intercept(actionDetails: ActionDetails) : Consequence? {
        if(this.granter.ammo <= 0) {
            return Consequence {
                println("The ${actionDetails.subject.name} is out of ammo")
            }
        }
        return null
    }
}