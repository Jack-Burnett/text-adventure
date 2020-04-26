import actions.Action
import attributes.Attribute
import kotlin.streams.toList

class Thing(val name:String, val attributes:MutableList<Attribute>) {

    fun describe():String {
        val sorted = this.attributes.sortedBy { a: Attribute -> a.classification }
        val description = sorted.stream().map { a -> a.description()}.toList().filterNotNull().distinct().joinToString()
        return (description + " " + this.name)
    }
}