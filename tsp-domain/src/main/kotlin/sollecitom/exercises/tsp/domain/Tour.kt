package sollecitom.exercises.tsp.domain

data class Tour<ELEMENT>(private val path: List<ELEMENT>) : List<ELEMENT> by path {

    constructor(vararg path: ELEMENT) : this(path.toList())

    init {
        require(path.first() != path.last())
        path.zipWithNext().forEach { cities -> require(cities.first != cities.second) }
    }

    override fun toString() = path.joinToString(" -> ", prefix = "[", postfix = "]")
}