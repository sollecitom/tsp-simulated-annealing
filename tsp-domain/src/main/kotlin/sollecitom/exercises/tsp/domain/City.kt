package sollecitom.exercises.tsp.domain

class City<LOCATION : Distant<DISTANCE, LOCATION>, DISTANCE : Comparable<DISTANCE>>(val name: String, val location: LOCATION) : Distant<DISTANCE, City<LOCATION, DISTANCE>> {

    override fun distanceFrom(other: City<LOCATION, DISTANCE>) = location.distanceFrom(other.location)
}

class TravellingSalesmanProblem<LOCATION : Distant<DISTANCE, LOCATION>, DISTANCE : Comparable<DISTANCE>>(val cities: Set<City<LOCATION, DISTANCE>>, private val distanceZero: DISTANCE, private val sumDistances: (DISTANCE, DISTANCE) -> DISTANCE) {

    fun totalDistance(tour: Tour<City<LOCATION, DISTANCE>>): DISTANCE = tour.zipWithNext().fold(distanceZero) { total, cities -> sumDistances.invoke(total, cities.second.distanceFrom(cities.first)) }

    fun acceptsAsSolution(tour: Tour<City<LOCATION, DISTANCE>>) = tour.toSet() == cities
}

class Tour<ELEMENT>(path: List<ELEMENT>) : List<ELEMENT> by path {

    constructor(vararg path: ELEMENT) : this(path.toList())

    init {
        require(path.first() == path.last())
    }
}