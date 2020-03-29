import actions.Action

class VerbManager {
    private val verbs = mutableMapOf<String, `Verb.kt`>()

    fun insert(action: Action, synonyms:List<VerbDefinition>) {
        for(def in synonyms) {
            val verb = `Verb.kt`(def.pastTense, def.presentTense, action)
            verbs[def.presentTense] = verb
        }
    }

    fun resolveToVerb(string:String): `Verb.kt`? {
        val string = string.toLowerCase()
        return verbs[string]
    }

    class VerbDefinition(pastTense:String, presentTense:String) {
        val pastTense = pastTense
        val presentTense = presentTense
    }

}
