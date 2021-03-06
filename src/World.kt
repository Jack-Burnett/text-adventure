import attributes.Contains
import context.ContextManager
import context.ContextSetEntry
import context.ContextSetRoom
import world.Area
import world.Connection
import kotlin.streams.toList

class World(var currentArea: Area, val areas:List<Area>, val connections:List<Connection>) {
    var heldItems = mutableListOf<Thing>()



    fun getThingsInArea():Set<Thing> {
        return _thingsInCurrentArea().union(_barriersInArea(currentArea))
    }
    fun getThingsInAreaAndContained():Set<Thing> {
        val things = getThingsInArea()
        val contained = _thingsInThings(things)
        return things.union(contained)
    }

    private fun _thingsInCurrentArea() : Set<Thing> {
        return currentArea.things()
    }

    private fun _barriersInArea(area: Area) : Set<Thing> {
        return connections.stream()
            .filter { con -> con.area1 == area || con.area2 == area }
            .flatMap { con -> con.barriers.stream() }
            .toList().toSet()
    }

    fun reachableAreas(area: Area) : Set<Area> {
        return connections.stream()
            .filter { con -> con.area1 == area || con.area2 == area }
            .filter { con -> con.isTraversable(this) }
            .map { con -> if (con.area1 == area) con.area2 else con.area1 }
            .toList().toSet()
    }

    private fun _thingsInThings(things : Set<Thing>) : MutableSet<Thing> {
        return things.stream()
            .flatMap { thing ->
                thing.attributes.stream()
                    .filter { attr -> attr is Contains }
                    .map { attr ->  attr as Contains }
                    .flatMap { attr -> attr.list().stream() }
                    .toList().toMutableList().stream()
            }
            .toList().toMutableSet()
    }

    fun describe(contextManager:ContextManager) {
        val things = getThingsInArea()

        var fullDescription = "You see " + context.describe(things)

        println(fullDescription)

        contextManager.publishContext(
            ContextSetRoom(currentArea, things.stream().map { thing -> ContextSetEntry(thing) }.toList().toSet())
        )
    }
}