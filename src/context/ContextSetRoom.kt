package context

import Area
import Thing

// Stores the state at a given point of time (as described to the player)
class ContextSetRoom(val area:Area, items:Set<ContextSetEntry>) : ContextSet(items) {

}