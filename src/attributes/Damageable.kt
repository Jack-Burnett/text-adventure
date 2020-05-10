package attributes

import Classification
import Consequence
import ConsequenceScope
import Parent
import actions.ActionDetails
import actions.definitions.Paint
import actions.definitions.Shoot

/**
 * Sturdiness is how much damage it can take; any damage less than resilience is ignored
 */
class Damageable(val sturdiness:Int, val resilience:Int) : Attribute("inside", Classification.STATE) {
    enum class Type {
        BULLET, PHYSICAL
    }
    class Instance(val type:Type, val amount:Int);

    private val instances = mutableListOf<Instance>()
    private var description:String? = null

    override fun description(): String? {
        return description
    }

    fun assess(actionDetails: ActionDetails, scope : ConsequenceScope) : Unit {
        val adjectives = mutableListOf<String>()
        val damage = instances
            .filter { it.amount > resilience }
            .sumBy { it.amount }

        if(damage >= sturdiness) {
            adjectives.add("destroyed")
        }
        if(instances.filter { it.type == Type.BULLET} .size >= 2 && adjectives.isEmpty()) {
            adjectives.add("bullet-riddled")
        }
        if(damage >= 3 && adjectives.isEmpty()) {
            adjectives.add("badly damaged")
        }
        if(instances.size >= 1 && adjectives.isEmpty()) {
            adjectives.add("damaged")
        }
        if(damage > sturdiness) {
            println("The ${actionDetails.subject.name} is destroyed")
        }
        if(adjectives.isEmpty()) {
            description = null
        } else {
            description = adjectives.joinToString()
        }
    }

    override fun actOn(actionDetails: ActionDetails):Consequence? {
        return when (actionDetails.action) {
            is Shoot -> {
                Consequence {
                    this.instances.add(
                        Instance(Type.BULLET, 2)
                    )
                    println("You shoot the ${actionDetails.subject.name}")
                    assess(actionDetails, it)
                }
            } else -> {
                null
            }
        }
    }

}