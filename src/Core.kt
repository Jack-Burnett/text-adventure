import actions.*
import actions.definitions.*
import context.ContextManager
import context.ContextSetEntry
import world.Area
import java.lang.RuntimeException

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
            actionManager.add(Look())

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

                    if(!canAccess(subject.thing)) {
                        println("I am unable to access $subject.thing")
                        continue
                    }

                    val actions:List<Action> = actionManager.actions(verb.action)
                    if(actions.isEmpty()) {
                        println("I am unable to ${verb.presentTense}")
                    } else {

                        val actionDetails = ActionDetails(actions[0], verb, subject.thing)
                        val consequence:Consequence? = actions[0].apply(actionDetails)
                        if(consequence != null) {
                            consequence.action.invoke(ConsequenceScope(world, contextManager))
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

    fun canAccess(sub:Thing) : Boolean {
        var subject = sub
        while(true) {
            var parent : Parent? = subject.getParent()
            if(parent == null) {
                println("PREVENT SUBJECT")
                return false
            }
            if(!parent.isAccessible(subject)) {
                println("PREVENT INACCESSIBLE")
                return false
            }
            if(parent is Area) {
                println("PREVENT AREA")
                return parent == world.currentArea
            } else
            if(parent is Thing) {
                subject = parent
            } else {
                throw RuntimeException()
            }

        }

    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}
