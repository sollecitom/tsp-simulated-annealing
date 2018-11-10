package sollecitom.exercises.tsp.domain

class City<LOCATION : Distant<DISTANCE, LOCATION>, DISTANCE : Comparable<DISTANCE>>(val name: String, val location: LOCATION) : Distant<DISTANCE, City<LOCATION, DISTANCE>> {

    override fun distanceFrom(other: City<LOCATION, DISTANCE>) = location.distanceFrom(other.location)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as City<*, *>
        if (name != other.name) return false
        return true
    }

    override fun hashCode() = name.hashCode()

    override fun toString() = name
}