package sollecitom.exercises.tsp.coordinates

data class Boundary<VALUE : Comparable<VALUE>>(val value: VALUE, val isInclusive: Boolean = true)