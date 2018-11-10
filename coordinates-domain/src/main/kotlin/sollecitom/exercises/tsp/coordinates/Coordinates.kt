package sollecitom.exercises.tsp.coordinates

interface Coordinates : Iterable<Pair<Dimension<*>, Any>> {

    val size: Int

    val system: Coordinates.System

    operator fun <VALUE : Comparable<VALUE>> get(dimension: Dimension<VALUE>): VALUE

    data class System(val dimensions: Set<Dimension<*>>) {

        constructor(vararg dimensions: Dimension<*>) : this(dimensions.toSet())

        operator fun contains(dimension: Dimension<*>): Boolean = dimensions.contains(dimension)

        operator fun contains(coordinates: Coordinates): Boolean = coordinates.system == this

        val size: Int = dimensions.size

        fun newCoordinates(action: (Coordinates.Builder) -> Unit): Coordinates = CoordinatesImpl.withSystem(this, action)
    }

    interface Builder {

        operator fun <VALUE : Comparable<VALUE>> set(dimension: Dimension<VALUE>, value: VALUE)
    }
}