import attributes.Attribute
import attributes.Contains
import kotlin.streams.toList

class ContextManager(private val world:World) {
    fun getThingsInArea():Set<Thing> {
        return _thingsInCurrentArea().union(_barriersInCurrentArea())
    }
    fun getThingsInAreaAndContained():Set<Thing> {
        val things = getThingsInArea()
        val contained = _thingsInThings(things)
        return things.union(contained)
    }

    private fun _thingsInCurrentArea() : Set<Thing> {
        return world.currentArea.contains.toSet()
    }
    private fun _barriersInCurrentArea() : Set<Thing> {
        return world.connections.stream()
            .filter { con -> con.area1 == world.currentArea || con.area2 == world.currentArea}
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

    fun describe(thing:Thing):String {
        val sorted = thing.attributes.sortedBy { a: Attribute -> a.classification }
        val description = sorted.stream().map { a -> a.description()}.toList().filterNotNull().distinct().joinToString()
        return (description + " " + thing.name)
    }

    fun describe() {
        val context = getThingsInArea()

        var fullDescription = "You see "
        fullDescription += context.stream()
            .map { thing -> describe(thing) }
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
