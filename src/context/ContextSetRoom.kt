package context

import world.Area
import Consequence
import World

// Stores the state at a given point of time (as described to the player)
class ContextSetRoom(val end: Area, items:Set<ContextSetEntry>) : ContextSet(items) {

    fun routeTo(world: World):List<Consequence> {
        val start = world.currentArea

        val openSet = mutableListOf(start)
        while (openSet.isNotEmpty()) {
            val area = openSet.removeAt(0)

            world.connections.stream()
                .filter{ con -> con.area1 == area || con.area2 == area }
        }
        return listOf()
    }

    fun _routeTo(world: World, target: Area, area: Area, route:List<Area>):List<Consequence>? {
        // Clone
        /*
        val route = mutableListOf<world.Area>().apply { addAll(alreadyReached) }
        route.add(area)

        if(area == target) {

        }

        // fuckin pathfinding or whatever

        val openSet = mutableListOf(start)
        while (openSet.isNotEmpty()) {
            val area = openSet.removeAt(0)

            world.reachableAreas()
        }
        */
        return listOf()

    }
}