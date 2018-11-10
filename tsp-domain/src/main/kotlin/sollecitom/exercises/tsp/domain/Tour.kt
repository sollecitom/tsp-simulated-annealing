package sollecitom.exercises.tsp.domain

data class Tour<ELEMENT>(private val path: List<ELEMENT>) : List<ELEMENT> by path {

    constructor(vararg path: ELEMENT) : this(path.toList())

    init {
        require(path.first() != path.last())
    }

    override fun toString() = path.joinToString(" -> ", prefix = "[", postfix = "]")
}