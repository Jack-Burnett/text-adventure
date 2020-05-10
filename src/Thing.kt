import attributes.Attribute
import attributes.ContainedIn
import attributes.SuperContainer
import kotlin.streams.toList

class Thing(val name:String, val attributes:MutableList<Attribute>) : Parent {

    fun describe():String {
        val sorted = this.attributes.sortedBy { a: Attribute -> a.classification }
        val description = sorted.stream().map { a -> a.description()}.toList().filterNotNull().distinct().joinToString()
        return (description + " " + this.name)
    }

    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun getParent():Parent? {
        return attributes.filterIsInstance<ContainedIn>().firstOrNull()?.parent
    }

    override fun removeChild(thing: Thing) {
        val containerAttribute = attributes.filterIsInstance<SuperContainer>()
            .firstOrNull { container -> container.list().contains(thing) }
        containerAttribute?.remove(thing)

    }

    override fun isAccessible(thing: Thing): Boolean {
        val stream = attributes.filterIsInstance<SuperContainer>()
        if(stream.isEmpty()) return false

        stream.filter { container -> container.contains(thing) }
        if(stream.isEmpty()) return false

        val prevented = stream.any { contains ->
            attributes.any { it.interceptsContents(thing, contains) }
        }
        return !prevented
    }
}