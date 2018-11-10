package sollecitom.exercises.tsp.domain

// TODO create interface, output permutations
class TravellingSalesmanProblem<LOCATION : Distant<DISTANCE, LOCATION>, DISTANCE : Comparable<DISTANCE>>(val cities: Set<City<LOCATION, DISTANCE>>, private val distanceZero: DISTANCE, private val sumDistances: (DISTANCE, DISTANCE) -> DISTANCE) {

    fun totalDistance(tour: Tour<City<LOCATION, DISTANCE>>): DISTANCE = tour.zipWithNext().fold(distanceZero) { total, cities -> sumDistances.invoke(total, cities.second.distanceFrom(cities.first)) }

    fun acceptsAsSolution(tour: Tour<City<LOCATION, DISTANCE>>) = tour.toSet() == cities

    fun allTours(): Sequence<Tour<City<LOCATION, DISTANCE>>> = cities.permutations().map(::Tour)
}

fun <LOCATION : Distant<DISTANCE, LOCATION>, DISTANCE : Comparable<DISTANCE>> TravellingSalesmanProblem<LOCATION, DISTANCE>.bruteForce(): Tour<City<LOCATION, DISTANCE>> {

    return allTours().minBy(::totalDistance)!!
}