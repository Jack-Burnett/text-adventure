package context

import World

class ContextManager(private val world: World) {
    private val history:MutableList<ContextSet> = mutableListOf()

    private val allRecognisedNouns:MutableSet<String> = mutableSetOf()

    fun publishContext(contextSet: ContextSet) {
        history.add(0, contextSet)
        allRecognisedNouns.addAll(contextSet.getNouns())
    }

    fun resolveNoun(noun:String) : List<ContextSetEntry> {
        if(allRecognisedNouns.contains(noun)) {
            for(contextSet in this.history) {
                val matches = contextSet.getMatches(noun)
                if(matches.isNotEmpty()) {
                    return matches
                }
            }
        }
        return listOf()
    }
}
