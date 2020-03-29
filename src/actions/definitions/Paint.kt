package actions.definitions

import Colour
import actions.Action
import actions.ActionName

class Paint(val colour: Colour) : Action(ActionName.PAINT)