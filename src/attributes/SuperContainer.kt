package attributes

import Classification
import Thing

abstract class SuperContainer(val name:String) : Attribute(name,
    Classification.STATE
) {
    protected var contents:MutableList<Thing> = mutableListOf()

    fun add(thing : Thing, owner:Thing) : Attribute {
        contents.add(thing)
        thing.attributes.add(ContainedIn(owner))

        return this
    }
    fun remove(thing : Thing) {
        contents.remove(thing)
        thing.attributes.removeIf { a -> a is ContainedIn }
    }
    fun contains(thing : Thing) : Boolean {
        return contents.contains(thing)
    }
    fun list() : List<Thing> {
        return contents.toList()
    }

}