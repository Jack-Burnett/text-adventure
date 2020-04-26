import actions.*
import actions.definitions.*
import context.ContextManager
import context.ContextSetEntry

class Core(val world:World) {
    private val verbManager = VerbManager()
    private val actionManager = ActionManager()
    private val contextManager = ContextManager(world)

    fun main() {

        while(true) {
            val context = world.getThingsInAreaAndContained()

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

            world.describe(contextManager)

            val line:String? = readLine()
            if(line != null) {
                val words = line.split(" ")

                val verbs = mutableListOf<Verb>()
                val subjects = mutableListOf<ContextSetEntry>()
                for(word in words) {
                    val verb = verbManager.resolveToVerb(word)
                    if(verb != null) {
                        verbs.add(verb)
                    }

                    subjects.addAll(contextManager.resolveNoun(word))
                }

                if(verbs.size == 1 && subjects.size == 1) {
                    val verb = verbs[0]
                    val subject = subjects[0]

                    val actions:List<Action> = actionManager.actions(verb.action)
                    if(actions.isEmpty()) {
                        println("I am unable to ${verb.presentTense}")
                    } else {

                        val actionDetails = ActionDetails(actions[0], verb, subject.thing, this)
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
