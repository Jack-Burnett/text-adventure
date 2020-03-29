import actions.Break
import actions.Close
import actions.Open
import actions.Paint
import attributes.Attribute
import attributes.Coloured
import attributes.Locked
import attributes.Sealable
import kotlin.streams.toList

fun describe(thing:Thing):String {
    val sorted = thing.attributes.sortedBy { a: Attribute -> a.classification }
    val description = sorted.joinToString { a -> a.toString() }
    return (description + " " + thing.name)
}

fun describe(context:List<Thing>) {
    var fullDescription = "You see "
    fullDescription += context.stream()
        .map { thing -> describe(thing) }
        .map { description ->
            val newDescription = if (description.matches(Regex("^[aeiou].*"))) {
                "an $description"
            } else {
                "a $description"
            }
            newDescription
        }
        .toList().joinToString(" and ")
    val index = fullDescription.lastIndexOf(" and ")
    if(index >= 0) {
        fullDescription = fullDescription.replaceRange(index..index+4, " and ")
    }
    println(fullDescription)
}

enum class Colour {
    RED, BLUE, GREEN, YELLOW
}

fun main() {
    val destroy = Break()
    val open = Open()
    val close = Close()
    val paint = Paint(Colour.RED)

    val verbManager = VerbManager()
    verbManager.insert(destroy,  listOf(
        VerbManager.VerbDefinition("broke", "break"),
        VerbManager.VerbDefinition("destroyed", "destroy"),
        VerbManager.VerbDefinition("smashed", "smash")
    ))
    verbManager.insert(open,  listOf(
        VerbManager.VerbDefinition("open", "open")
    ))
    verbManager.insert(close,  listOf(
        VerbManager.VerbDefinition("close", "close")
    ))
    verbManager.insert(paint,  listOf(
        VerbManager.VerbDefinition("paint", "paint")
    ))

    val door = Thing("door", mutableListOf(
        Coloured(Colour.RED),
        Sealable(Sealable.State.CLOSED),
        Locked { (locked:Locked, thing:Thing) -> true }))

    val chest = Thing("chest", mutableListOf(Coloured(Colour.BLUE), Sealable(Sealable.State.CLOSED)))
    val gate = Thing("gate", mutableListOf(Coloured(Colour.GREEN), Sealable(Sealable.State.OPEN)))

    val context = mutableListOf<Thing>(door, chest, gate)

    while(true) {
        describe(context)

        val line:String? = readLine()
        if(line != null) {
            val words = line.split(" ")

            val verbs = mutableListOf<Verb>();
            val subjects = mutableListOf<Thing>()
            for(word in words) {
                val verb = verbManager.resolveToVerb(word)
                if(verb != null) {
                    verbs.add(verb)
                }

                for(item in context) {
                    if(item.name == word) {
                        subjects.add(item)
                        break;
                    }
                }
            }

            if(verbs.size == 1 && subjects.size == 1) {
                val verb = verbs[0]
                val subject = subjects[0]

                verb.action.apply(subject, verb)

            } else {
                println("I don't know how to $line")
            }
        }
    }
}

class Thing(val name:String, val attributes:MutableList<Attribute>) {

}


class Consequence {

}