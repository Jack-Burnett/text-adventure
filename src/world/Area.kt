package world

import Parent
import Thing
import attributes.ContainedIn

class Area(things:List<Thing>) : Parent {

    init {
        things.forEach {
            thing -> thing.attributes.add(ContainedIn(this))
        }
    }

    private val contains:MutableList<Thing> = things.toMutableList()

    fun things() : Set<Thing> {
     return contains.toSet()
    }

    override fun removeChild(thing: Thing) {
        contains.remove(thing)
        thing.attributes.removeIf { a -> a is ContainedIn }
    }

    override fun isAccessible(thing: Thing): Boolean {
        return contains.contains(thing)
    }

    override fun getParent(): Parent? {
        return null
    }

 }