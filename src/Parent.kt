public interface Parent {
    fun removeChild(thing:Thing)
    fun isAccessible(thing:Thing) : Boolean
    fun getParent() : Parent?
}
