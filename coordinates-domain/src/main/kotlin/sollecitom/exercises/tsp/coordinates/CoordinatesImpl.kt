package sollecitom.exercises.tsp.coordinates

internal class CoordinatesImpl private constructor(private val valuesByDimension: Map<Dimension<*>, Any>, override val system: Coordinates.System) : Coordinates {

    companion object {

        internal fun withSystem(system: Coordinates.System, action: Coordinates.Builder.() -> Unit): CoordinatesImpl {
            val coordinates = CoordinatesImpl.Builder()
            action.invoke(coordinates)
            return coordinates.build(system)
        }
    }

    init {
        require(valuesByDimension.keys == system.dimensions)
    }

    override val size: Int = system.size

    override fun iterator(): Iterator<Pair<Dimension<*>, Any>> = valuesByDimension.entries.asSequence().map { it.toPair() }.iterator()

    @Suppress("UNCHECKED_CAST")
    override operator fun <VALUE : Comparable<VALUE>> get(dimension: Dimension<VALUE>): VALUE {
        require(dimension in system)
        return valuesByDimension[dimension] as VALUE
    }

    class Builder : Coordinates.Builder {

        private val valuesByDimension = mutableMapOf<Dimension<*>, Any>()

        override operator fun <VALUE : Comparable<VALUE>> set(dimension: Dimension<VALUE>, value: VALUE) {
            require(value in dimension)
            valuesByDimension[dimension] = value
        }

        internal fun build(system: Coordinates.System): CoordinatesImpl = CoordinatesImpl(valuesByDimension, system)
    }
}