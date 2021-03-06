package sollecitom.exercises.tsp.domain

interface TravellingSalesmanProblem<LOCATION : Distant<DISTANCE, LOCATION>, DISTANCE : Comparable<DISTANCE>> {

    val cities: Set<City<LOCATION, DISTANCE>>

    fun totalDistance(tour: Tour<City<LOCATION, DISTANCE>>): DISTANCE

    fun acceptsAsSolution(tour: Tour<City<LOCATION, DISTANCE>>): Boolean

    fun allTours(): Sequence<Pair<Tour<City<LOCATION, DISTANCE>>, DISTANCE>>

    fun randomTour(): Tour<City<LOCATION, DISTANCE>> = Tour(cities.shuffled())

    companion object {
        fun <LOCATION : Distant<DISTANCE, LOCATION>, DISTANCE : Comparable<DISTANCE>> of(cities: Set<City<LOCATION, DISTANCE>>, distanceZero: DISTANCE, sumDistances: (DISTANCE, DISTANCE) -> DISTANCE): TravellingSalesmanProblem<LOCATION, DISTANCE> = TravellingSalesmanProblemImpl(cities, distanceZero, sumDistances)

        fun <LOCATION : Distant<Double, LOCATION>> withDoubleAsDistance(cities: Set<City<LOCATION, Double>>) = of(cities, 0.0, Double::plus)
    }
}

private class TravellingSalesmanProblemImpl<LOCATION : Distant<DISTANCE, LOCATION>, DISTANCE : Comparable<DISTANCE>>(override val cities: Set<City<LOCATION, DISTANCE>>, private val distanceZero: DISTANCE, private val sumDistances: (DISTANCE, DISTANCE) -> DISTANCE) : TravellingSalesmanProblem<LOCATION, DISTANCE> {

    override fun totalDistance(tour: Tour<City<LOCATION, DISTANCE>>): DISTANCE = tour.zipWithNext().fold(distanceZero) { total, cities -> sumDistances.invoke(total, cities.second.distanceFrom(cities.first)) }

    override fun acceptsAsSolution(tour: Tour<City<LOCATION, DISTANCE>>) = tour.toSet() == cities

    override fun allTours(): Sequence<Pair<Tour<City<LOCATION, DISTANCE>>, DISTANCE>> = cities.permutations().map(::Tour).map { it to totalDistance(it) }
}

fun <LOCATION : Distant<DISTANCE, LOCATION>, DISTANCE : Comparable<DISTANCE>> TravellingSalesmanProblem<LOCATION, DISTANCE>.bruteForceSolution(): Pair<Tour<City<LOCATION, DISTANCE>>, DISTANCE> {

    return allTours().minBy { it.second }!!
}

// TODO move, refactor
// TODO introduce SimulatedAnnealing-compatible interface defining `deriveNextSolution` and `calculateCost`. Then, create an extension function or an interface function `approximateSolutionBySimulatedAnnealing()`
class SimulatedAnnealing<SOLUTION>(private val initialTemperature: Double = 10000.0, private val coolingRate: Double = 0.003, private val deriveNextSolution: (SOLUTION) -> SOLUTION, private val calculateCost: (SOLUTION) -> Double) {

    fun run(initialSolution: SOLUTION): Pair<SOLUTION, Double> {

        // Set initial temp
        var temp = initialTemperature

        var best = initialSolution to calculateCost.invoke(initialSolution)
        var current = initialSolution to calculateCost.invoke(initialSolution)
        // Loop until system has cooled
        while (temp > 1) {
            // Create new neighbour tour
            val next = deriveNextSolution.invoke(current.first).let { it to calculateCost.invoke(it) }

            // Get energy of solutions
            val currentEnergy = current.second
            val neighbourEnergy = next.second

            // Decide if we should accept the neighbour
            if (acceptanceProbability(currentEnergy, neighbourEnergy, temp) > Math.random()) {
                current = next
            }

            // Keep track of the best solution found
            if (current.second < best.second) {
                best = current
            }

            // Cool system
            temp *= 1 - coolingRate
        }
        return best
    }

    // Calculate the acceptance probability
    private fun acceptanceProbability(energy: Double, newEnergy: Double, temperature: Double): Double {
        // If the new solution is better, accept it
        return when {
            newEnergy < energy -> 1.0
            else -> Math.exp((energy - newEnergy) / (energy * temperature))
            // TODO check if this normalization about works
//            else -> Math.exp((energy - newEnergy) / temperature)
        }
        // If the new solution is worse, calculate an acceptance probability
    }
}