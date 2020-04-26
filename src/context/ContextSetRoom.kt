package context

import Area
import Consequence
import World

// Stores the state at a given point of time (as described to the player)
class ContextSetRoom(val area:Area, items:Set<ContextSetEntry>) : ContextSet(items) {
    fun routeTo(world: World):Consequence {
        val start = world.currentArea
        val end = area

        val openSet = mutableListOf(start)
        for (area in openSet) {
            world.connections.stream()
                .filter{ con -> con.area1 == area || con.area2 == area }
            world.connections.get(0).area1
            world.connections.get()
        }

    }
}