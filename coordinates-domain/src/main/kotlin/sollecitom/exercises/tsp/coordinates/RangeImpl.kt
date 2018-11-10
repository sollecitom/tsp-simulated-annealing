package sollecitom.exercises.tsp.coordinates

internal data class RangeImpl<VALUE : Comparable<VALUE>>(override val lowerBound: Boundary<VALUE>?, override val upperBound: Boundary<VALUE>?) : Range<VALUE>