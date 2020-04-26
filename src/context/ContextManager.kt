package context

import Thing
import World
import attributes.Attribute
import attributes.Contains
import kotlin.streams.toList

class ContextManager(private val world: World) {
    val history:List<ContextSet> = listOf()

}
