import world.Connection
import attributes.*

fun main() {

    val door = Thing("door", mutableListOf(
        Coloured(Colour.RED),
        Sealable(Sealable.State.OPEN)//,
        //Locked { locked: Locked, thing:Thing -> true }
    ))

    val redPaint = Thing("paintpot", mutableListOf(Coloured(Colour.RED), Colours(Colour.RED), SmallItem()))
    val chest = Thing("chest", mutableListOf(Coloured(Colour.BLUE), Sealable(Sealable.State.CLOSED),
    Contains(mutableListOf(redPaint))))


    val room1 = Area(mutableListOf(chest))
    val room2 = Area(mutableListOf())

    val connection = Connection(room1, room2, mutableListOf(door))

    val world = World(room1, listOf(room1, room2), listOf(connection))
    Core(world).main()
}