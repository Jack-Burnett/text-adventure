import actions.*
import actions.definitions.*
import attributes.*
import kotlin.streams.toList

class Core {
    val verbManager = VerbManager()
    val actionManager = ActionManager()
    val context = mutableListOf<Thing>()

    val heldItems = mutableListOf<Thing>()

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

    fun init() {

    }

    fun main() {

        val door = Thing("door", mutableListOf(
            Coloured(Colour.RED),
            Sealable(Sealable.State.CLOSED),
            Locked { locked:Locked, thing:Thing -> true }))

        val chest = Thing("chest", mutableListOf(Coloured(Colour.BLUE), Sealable(Sealable.State.CLOSED)))
        val gate = Thing("gate", mutableListOf(Coloured(Colour.GREEN), Sealable(Sealable.State.OPEN)))
        val redPaint = Thing("paintpot", mutableListOf(Coloured(Colour.RED), Colours(Colour.RED), SmallItem()))

        context.addAll(listOf(door, chest, gate, redPaint))

        while(true) {
            actionManager.reset()
            actionManager.add(Break())
            actionManager.add(Open())
            actionManager.add(Close())
            actionManager.add(Pickup())

            for(item in this.heldItems) {
                for(attr in item.attributes) {
                    actionManager.addAll(attr.grantActions())
                }
            }
            //actionManager.add(Paint(Colour.RED))
            //actionManager.add(Unlock())


            describe(context)

            val line:String? = readLine()
            if(line != null) {
                val words = line.split(" ")

                val verbs = mutableListOf<Verb>()
                val subjects = mutableListOf<Thing>()
                for(word in words) {
                    val verb = verbManager.resolveToVerb(word)
                    if(verb != null) {
                        verbs.add(verb)
                    }

                    for(item in context) {
                        if(item.name == word) {
                            subjects.add(item)
                            break
                        }
                    }
                }

                if(verbs.size == 1 && subjects.size == 1) {
                    val verb = verbs[0]
                    val subject = subjects[0]

                    val actions:List<Action> = actionManager.actions(verb.action)
                    if(actions.isEmpty()) {
                        println("I am unable to ${verb.presentTense}")
                    } else {
                        val actionDetails = ActionDetails(actions[0], verb, subject, this)
                        val consequence:Consequence? = actions[0].apply(actionDetails)
                        if(consequence != null) {
                            consequence.action.invoke()
                        } else {
                            println("Nothing happened")
                        }
                    }

                } else {
                    println("I don't know how to $line")
                }
            }
        }
    }
}
