package actions

class VerbManager {
    private val verbs = mutableMapOf<String, Verb>()

    init {
        insert(ActionName.DESTROY,  listOf(
            VerbDefinition("broke", "break"),
            VerbDefinition("destroyed", "destroy"),
            VerbDefinition("smashed", "smash")
        ))
        insert(ActionName.OPEN,  listOf(
            VerbDefinition("open", "open")
        ))
        insert(ActionName.CLOSE,  listOf(
            VerbDefinition("close", "close")
        ))
        insert(ActionName.PAINT,  listOf(
            VerbDefinition("paint", "paint")
        ))
        insert(ActionName.UNLOCK,  listOf(
            VerbDefinition("unlocked", "unlock")
        ))
        insert(ActionName.PICKUP,  listOf(
            VerbDefinition("pickup", "pickup"),
            VerbDefinition("took", "take")
        ))
        insert(ActionName.ENTER,  listOf(
            VerbDefinition("enter", "enter")
        ))
        insert(ActionName.LOOK,  listOf(
            VerbDefinition("look", "look")
        ))
    }

    private fun insert(action: ActionName, synonyms:List<VerbDefinition>) {
        for(def in synonyms) {
            val verb = Verb(def.pastTense, def.presentTense, action)
            verbs[def.presentTense] = verb
        }
    }

    fun resolveToVerb(string:String): Verb? {
        val string = string.toLowerCase()
        return verbs[string]
    }

    class VerbDefinition(pastTense:String, presentTense:String) {
        val pastTense = pastTense
        val presentTense = presentTense
    }

}
