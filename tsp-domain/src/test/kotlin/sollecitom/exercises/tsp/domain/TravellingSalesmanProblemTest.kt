package sollecitom.exercises.tsp.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class TravellingSalesmanProblemTest {

    @Test
    fun acceptance_criteria() {

        val distances = mutableMapOf<Pair<String, String>, Double>()

        distances["Toronto" to "Rome"] = 5000.0
        distances["Rome" to "Florence"] = 200.0
        distances["Florence" to "Toronto"] = 5100.0

        val cities = locations(distances.symmetric(), 0.0, Double.POSITIVE_INFINITY).map { City(it.token, it) }.toSet()

        val florence = cities.single { it.name == "Florence" }
        val toronto = cities.single { it.name == "Toronto" }
        val rome = cities.single { it.name == "Rome" }

        val problem = TravellingSalesmanProblem.withDoubleAsDistance(cities)

        val tour = Tour(rome, toronto, florence)

        assertThat(problem.cities).isEqualTo(cities)
        assertThat(problem.acceptsAsSolution(tour)).isEqualTo(true)
        assertThat(problem.totalDistance(tour)).isEqualTo(toronto.distanceFrom(rome) + florence.distanceFrom(toronto))

        val invalidTour = Tour(rome, toronto)
        assertThat(problem.acceptsAsSolution(invalidTour)).isEqualTo(false)
    }

    @Test
    fun solution() {
        val distances = mutableMapOf<Pair<String, String>, Double>()

        distances["Toronto" to "Rome"] = 5000.0
        distances["Rome" to "Florence"] = 200.0
        distances["Florence" to "Toronto"] = 5100.0
        distances["Toronto" to "Milan"] = 4500.0
        distances["Milan" to "Rome"] = 450.0

        val cities = locations(distances.symmetric(), 0.0, Double.POSITIVE_INFINITY).map { City(it.token, it) }.toSet()

        val problem = TravellingSalesmanProblem.withDoubleAsDistance(cities)

        println("All possible tours:")
        problem.allTours().filter { it.second != Double.POSITIVE_INFINITY }.forEach { println("${it.first}: ${it.second}") }

        val solution = problem.bruteForceSolution()

        println()
        println("Optimal tours:")
        problem.allTours().filter { it.second == solution.second }.forEach { println("${it.first}: ${it.second}") }
    }

    @Test
    fun euristic() {

        val distances = mutableMapOf<Pair<String, String>, Double>()

        distances["Toronto" to "Rome"] = 5000.0
        distances["Rome" to "Florence"] = 200.0
        distances["Florence" to "Toronto"] = 5100.0
        distances["Toronto" to "Milan"] = 4500.0
        distances["Milan" to "Rome"] = 450.0

        val cities = locations(distances.symmetric(), 0.0, Double.POSITIVE_INFINITY).map { City(it.token, it) }.toSet()

        val problem = TravellingSalesmanProblem.withDoubleAsDistance(cities)

        fun nextSolutionCandidate(tour: Tour<City<TokenizedRelativeLocation<Double, String>, Double>>): Tour<City<TokenizedRelativeLocation<Double, String>, Double>> {
            val newTour = tour.toMutableList()
            val first = (tour.size * Math.random()).toInt()
            val second = (tour.size * Math.random()).toInt()

            newTour[first] = tour[second]
            newTour[second] = tour[first]

            // TODO refactor
            val nextTour = try {
                Tour(newTour)
            } catch (e: IllegalArgumentException) {
                nextSolutionCandidate(tour)
            }
            return if (problem.acceptsAsSolution(nextTour)) nextTour else nextSolutionCandidate(tour)
        }

        val simulation = SimulatedAnnealing(deriveNextSolution = ::nextSolutionCandidate, calculateCost = problem::totalDistance)

        val approximateBest = simulation.run(problem.randomTour())

        println("Approximate optimal tour:")
        println("${approximateBest.first}: ${approximateBest.second}")
    }
}