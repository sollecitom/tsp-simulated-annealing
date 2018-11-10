package sollecitom.exercises.tsp.coordinates

class Dimension<VALUE : Comparable<VALUE>>(val name: String, private val range: Range<VALUE>) : Range<VALUE> by range {

    constructor(name: String, closedRange: ClosedRange<VALUE>) : this(name, RangeImpl(Boundary(closedRange.start), Boundary(closedRange.endInclusive)))

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Dimension<*>
        if (name != other.name) return false
        return true
    }

    override fun hashCode() = name.hashCode()
}