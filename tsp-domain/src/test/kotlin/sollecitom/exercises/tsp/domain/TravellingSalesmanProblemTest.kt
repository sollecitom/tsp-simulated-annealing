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

        val cities = locations(distances.symmetric(), 0.0).map { City(it.token, it) }.toSet()

        val florence = cities.single { it.name == "Florence"}
        val toronto = cities.single { it.name == "Toronto"}
        val rome = cities.single { it.name == "Rome"}

        val problem = TravellingSalesmanProblem(cities, 0.0, Double::plus)

        val tour = Tour(rome, toronto, florence, rome)

        assertThat(problem.cities).isEqualTo(cities)
        assertThat(problem.acceptsAsSolution(tour)).isEqualTo(true)
        assertThat(problem.totalDistance(tour)).isEqualTo(toronto.distanceFrom(rome) + florence.distanceFrom(toronto) + rome.distanceFrom(florence))

        val invalidTour = Tour(rome, toronto, rome)
        assertThat(problem.acceptsAsSolution(invalidTour)).isEqualTo(false)
    }
}