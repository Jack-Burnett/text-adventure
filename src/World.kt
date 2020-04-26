import attributes.Contains
import world.Connection
import kotlin.streams.toList

class World(var currentArea:Area, val areas:List<Area>, val connections:List<Connection>) {
    var heldItems = mutableListOf<Thing>()

    fun getThingsInArea():Set<Thing> {
        return _thingsInCurrentArea().union(_barriersInCurrentArea())
    }
    fun getThingsInAreaAndContained():Set<Thing> {
        val things = getThingsInArea()
        val contained = _thingsInThings(things)
        return things.union(contained)
    }

    private fun _thingsInCurrentArea() : Set<Thing> {
        return currentArea.contains.toSet()
    }
    private fun _barriersInCurrentArea() : Set<Thing> {
        return connections.stream()
            .filter { con -> con.area1 == currentArea || con.area2 == currentArea}
            .flatMap { con -> con.barriers.stream() }
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

    fun describe() {
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
    }
}