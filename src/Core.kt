import actions.*
import actions.definitions.*
import attributes.*
import kotlin.streams.toList

class Core(val world:World) {
    private val verbManager = VerbManager()
    private val actionManager = ActionManager()
    private val contextManager = ContextManager(world)

    fun main() {

        while(true) {
            val context = contextManager.getThingsInAreaAndContained()

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

            contextManager.describe()

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
