package actions

class ActionManager {
    private val actions = mutableMapOf<ActionName, MutableList<Action>>()

    fun actions(name:ActionName):List<Action> {
        return actions.getOrDefault(name, emptyList())
    }

    fun reset() {
        actions.clear()
    }

    fun add(action:Action) {
        if(!actions.containsKey(action.name)) {
            actions[action.name] = mutableListOf()
        }
        actions[action.name]?.add(action)
    }

}