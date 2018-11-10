package sollecitom.exercises.tsp.coordinates

interface Range<VALUE : Comparable<VALUE>> {

    val lowerBound: Boundary<VALUE>?
    val upperBound: Boundary<VALUE>?

    operator fun contains(value: VALUE): Boolean {
        lowerBound?.let {
            if (it.isInclusive && it.value > value || !it.isInclusive && it.value == value) return false
        }
        upperBound?.let {
            if (it.isInclusive && it.value < value || !it.isInclusive && it.value == value) return false
        }
        return true
    }

    fun isEmpty(): Boolean {
        return when {
            !isClosed() -> false
            lowerBound!!.value > upperBound!!.value -> false
            lowerBound!!.isInclusive && upperBound!!.isInclusive && lowerBound!!.value == upperBound!!.value -> false
            else -> true
        }
    }

    fun isClosed(): Boolean = lowerBound != null && upperBound != null
}