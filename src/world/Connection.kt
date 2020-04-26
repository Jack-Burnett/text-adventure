package world

import Area
import Thing
import attributes.Connects

class Connection(val area1:Area, val area2:Area, val barriers:MutableList<Thing>) {
    init {
        for(thing in this.barriers) {
            thing.attributes.add(Connects(this))
        }
    }
}