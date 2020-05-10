package attributes

import Consequence
import Thing
import actions.ActionDetails
import actions.definitions.*
import context.ContextSetEntry
import context.ContextSetRoom
import kotlin.streams.toList

class Contains(contents:MutableList<Thing>) : SuperContainer("contains", contents
) {

    override fun toString(): String {
        return "full"
    }

    override fun actOn(actionDetails: ActionDetails):Consequence? {
        return when (actionDetails.action) {
            is Look -> {
                Consequence {
                    println("The ${actionDetails.subject.name} contains " + context.describe(contents))
                    it.context.publishContext(
                        ContextSetRoom(it.world.currentArea, contents.stream().map { thing -> ContextSetEntry(thing) }.toList().toSet())
                    )
                }
            }
            else -> {
                null
            }
        }
    }
}