import world.Connection

class World(var currentArea:Area, val areas:List<Area>, val connections:List<Connection>) {
    var heldItems = mutableListOf<Thing>()
}