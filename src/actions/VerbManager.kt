package actions

class VerbManager {
    private val verbs = mutableMapOf<String, Verb>()

    fun insert(action: ActionName, synonyms:List<VerbDefinition>) {
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
