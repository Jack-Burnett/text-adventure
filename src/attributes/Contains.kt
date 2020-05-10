package attributes

import Consequence
import Thing
import actions.ActionDetails
import actions.definitions.*
import context.ContextSetEntry
import context.ContextSetRoom
import kotlin.streams.toList

class Contains : SuperContainer("contains") {

    override fun description(): String? {
        return null
    }

    override fun actOn(actionDetails: ActionDetails):Consequence? {
        return when (actionDetails.action) {
            is Look -> {
                Consequence {
                    if(contents.isEmpty()) {
                        println("The ${actionDetails.subject.name} is empty")
                    } else {
                        println("The ${actionDetails.subject.name} contains " + context.describe(contents))
                    }
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