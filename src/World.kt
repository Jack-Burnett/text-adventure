import attributes.Contains
import context.ContextManager
import context.ContextSetEntry
import context.ContextSetRoom
import world.Connection
import kotlin.streams.toList

class World(var currentArea:Area, val areas:List<Area>, val connections:List<Connection>) {
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
        return currentArea.contains.toSet()
    }

    private fun _barriersInArea(area: Area) : Set<Thing> {
        return connections.stream()
            .filter { con -> con.area1 == area || con.area2 == area }
            .flatMap { con -> con.barriers.stream() }
            .toList().toSet()
    }

    fun reachableAreas(area:Area) : Set<Area> {
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
                    .flatMap { attr -> attr.contents().stream() }
                    .toList().toMutableList().stream()
            }
            .toList().toMutableSet()
    }

    fun describe(contextManager:ContextManager) {
        val context = getThingsInArea()

        var fullDescription = "You see "
        fullDescription += context.stream()
            .map { thing -> thing.describe() }
            .map { description ->
                val newDescription = if (description.matches(Regex("^[aeiou].*"))) {
                    "an $description"
                } else {
                    "a $description"
                }
                newDescription
            }
            .toList().joinToString(" and ")
        val index = fullDescription.lastIndexOf(" and ")
        if(index >= 0) {
            fullDescription = fullDescription.replaceRange(index..index+4, " and ")
        }
        println(fullDescription)

        contextManager.publishContext(
            ContextSetRoom(currentArea, context.stream().map { thing -> ContextSetEntry(thing) }.toList().toSet())
        )
    }
}