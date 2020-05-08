package world

import Area
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
    fun isTraversable(world : World):Boolean {
        var result = false
        val realCurrentArea = world.currentArea
        world.currentArea = area1
        for(barrier in barriers) {
            for(attribute in barrier.attributes) {
                if(attribute is Connects) {
                    val action : ActionDetails = ActionDetails(
                        Enter(),
                        Verb("", "", ActionName.ENTER),
                        barrier,
                        world
                    )
                    if(attribute.actOn(action) != null) {
                        result = true
                    }
                }
            }
        }
        world.currentArea = realCurrentArea
        return result
    }
}