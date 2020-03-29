package actions

import Core
import Thing

class ActionDetails(
    val action: Action,
    val verb: Verb,
    val subject:Thing,
    val core:Core
)