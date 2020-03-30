import actions.*
import actions.definitions.*
import areas.Connection
import attributes.*
import kotlin.streams.toList

class Core(val world:World) {
    private val verbManager = VerbManager()
    private val actionManager = ActionManager()

    fun describe(thing:Thing):String {
        val sorted = thing.attributes.sortedBy { a: Attribute -> a.classification }
        val description = sorted.stream().map { a -> a.description()}.toList().filterNotNull().distinct().joinToString()
        return (description + " " + thing.name)
    }

    fun describe(context:Set<Thing>) {
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

    class World(var currentArea:Area, val areas:List<Area>, val connections:List<Connection>) {
        val heldItems = mutableListOf<Thing>()

        fun getContext():Set<Thing> {
            return connections.stream()
                .filter { con -> con.area1 == currentArea || con.area2 == currentArea}
                .flatMap { con -> con.barriers.stream() }
                .toList().union(currentArea.contains)
        }
    }

    fun main() {

        while(true) {
            val context = world.getContext()

            actionManager.reset()
            actionManager.add(Break())
            actionManager.add(Open())
            actionManager.add(Close())
            actionManager.add(Pickup())
            actionManager.add(Enter())

            for(item in this.world.heldItems) {
                for(attr in item.attributes) {
                    actionManager.addAll(attr.grantActions())
                }
            }
            //actionManager.add(Paint(Colour.RED))
            //actionManager.add(Unlock())


            describe(world.getContext())

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

                    for(item in world.getContext()) {
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
