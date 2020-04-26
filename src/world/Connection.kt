package world

import Area
import Core
import Thing
import World
import actions.ActionDetails
import actions.ActionName
import actions.Verb
import actions.definitions.Enter
import attributes.Connects

class Connection(val area1:Area, val area2:Area, val barriers:MutableList<Thing>) {
    init {
        for(thing in this.barriers) {
            thing.attributes.add(Connects(this))
        }
    }

    // Used for pathfinding
    fun isTraversable(core : Core):Boolean {
        var result = false
        val realCurrentArea = core.world.currentArea
        core.world.currentArea = area1
        for(barrier in barriers) {
            for(attribute in barrier.attributes) {
                if(attribute is Connects) {
                    val action : ActionDetails = ActionDetails(
                        Enter(),
                        Verb("", "", ActionName.ENTER),
                        barrier,
                        core
                    )
                    if(attribute.actOn(action) != null) {
                        result = true
                    }
                }
            }
        }
        core.world.currentArea = realCurrentArea
        return result
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}