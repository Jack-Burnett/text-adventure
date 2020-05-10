package actions

import Thing
import World

class ActionDetails(
    val action: Action,
    val verb: Verb,
    val subject:Thing
)