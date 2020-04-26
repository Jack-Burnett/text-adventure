package context

import Thing
import kotlin.streams.toList

// Stores the state at a given point of time (as described to the player)
abstract class ContextSet(val items:Set<ContextSetEntry>) {
    fun getNouns():List<String> {
        return items.stream().map { entry -> entry.thing.name }.toList()
    }
    fun getMatches(noun : String):List<ContextSetEntry> {
        return items.stream().filter { item -> item.thing.name == noun }.toList()
    }
}