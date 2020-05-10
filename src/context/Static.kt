package context

import Thing
import kotlin.streams.toList

fun describe(things : Collection<Thing>)  :String {
    var fullDescription = things.stream()
        .map { thing -> thing.describe() }
        .map { description ->
            val newDescription = if (description.matches(Regex("^[aeiou].*"))) {
                "an $description"
            } else {
                "a $description"
            }
            newDescription
        }
        .toList().joinToString(" and ")
    val index = fullDescription.lastIndexOf(" and ")
    if(index >= 0) {
        fullDescription = fullDescription.replaceRange(index..index+4, " and ")
    }
    return fullDescription
}