package sollecitom.exercises.tsp.domain

interface TravellingSalesmanProblem<LOCATION : Distant<DISTANCE, LOCATION>, DISTANCE : Comparable<DISTANCE>> {

    val cities: Set<City<LOCATION, DISTANCE>>

    fun totalDistance(tour: Tour<City<LOCATION, DISTANCE>>): DISTANCE

    fun acceptsAsSolution(tour: Tour<City<LOCATION, DISTANCE>>): Boolean

    fun allTours(): Sequence<Tour<City<LOCATION, DISTANCE>>>

    companion object {
        fun <LOCATION : Distant<DISTANCE, LOCATION>, DISTANCE : Comparable<DISTANCE>> of(cities: Set<City<LOCATION, DISTANCE>>, distanceZero: DISTANCE, sumDistances: (DISTANCE, DISTANCE) -> DISTANCE): TravellingSalesmanProblem<LOCATION, DISTANCE> = TravellingSalesmanProblemImpl(cities, distanceZero, sumDistances)

        fun <LOCATION : Distant<Double, LOCATION>> withDoubleAsDistance(cities: Set<City<LOCATION, Double>>) = of(cities, 0.0, Double::plus)
    }
}

private class TravellingSalesmanProblemImpl<LOCATION : Distant<DISTANCE, LOCATION>, DISTANCE : Comparable<DISTANCE>>(override val cities: Set<City<LOCATION, DISTANCE>>, private val distanceZero: DISTANCE, private val sumDistances: (DISTANCE, DISTANCE) -> DISTANCE) : TravellingSalesmanProblem<LOCATION, DISTANCE> {

    override fun totalDistance(tour: Tour<City<LOCATION, DISTANCE>>): DISTANCE = tour.zipWithNext().fold(distanceZero) { total, cities -> sumDistances.invoke(total, cities.second.distanceFrom(cities.first)) }

    override fun acceptsAsSolution(tour: Tour<City<LOCATION, DISTANCE>>) = tour.toSet() == cities

    override fun allTours(): Sequence<Tour<City<LOCATION, DISTANCE>>> = cities.permutations().map(::Tour)
}

fun <LOCATION : Distant<DISTANCE, LOCATION>, DISTANCE : Comparable<DISTANCE>> TravellingSalesmanProblem<LOCATION, DISTANCE>.bruteForce(): Tour<City<LOCATION, DISTANCE>> {

    return allTours().minBy(::totalDistance)!!
}