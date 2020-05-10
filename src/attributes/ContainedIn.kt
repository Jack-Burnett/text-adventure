package attributes

import Classification
import Parent

class ContainedIn(var parent:Parent) : Attribute("inside", Classification.STATE) {

    override fun description(): String? {
        return null
    }

}