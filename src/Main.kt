import world.Connection
import attributes.*
import world.Area

fun main() {

    val door = Thing("door", mutableListOf(
        Coloured(Colour.RED),
        Sealable(Sealable.State.OPEN)//,
        //Locked { locked: Locked, thing:Thing -> true }
    ))

    val redPaint = Thing("paintpot", mutableListOf(Coloured(Colour.RED), Colours(Colour.RED), SmallItem()))
    val chest = Thing("chest", mutableListOf(Coloured(Colour.BLUE), Sealable(Sealable.State.OPEN)))
    chest.attributes.add(Contains().add(redPaint, chest))


    val ash = Thing("ash", mutableListOf())
    ash.attributes.add(
        Coloured(Colour.GREY)
    )

    val ashtray = Thing("ashtray", mutableListOf())
    ashtray.attributes.add(
        Contains().add(ash, ashtray)
    )

    val gun = Thing("gun", mutableListOf(SmallItem()))
    gun.attributes.add(
        Shoots(6)
    )

    val desk = Thing("desk", mutableListOf(
        Damageable(1, 2)
    ))
    desk.attributes.add(
        Surface().add(ashtray, desk).add(gun, desk)
    )

    val room1 = Area(mutableListOf(desk))
    val room2 = Area(mutableListOf())

    val connection = Connection(room1, room2, mutableListOf(door))

    val world = World(room1, listOf(room1, room2), listOf(connection))
    Core(world).main()
}