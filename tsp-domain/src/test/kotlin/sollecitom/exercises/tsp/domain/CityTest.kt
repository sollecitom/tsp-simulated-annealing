package sollecitom.exercises.tsp.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CityTest {

    @Test
    fun distanceFrom() {

        val distances = mutableMapOf<Pair<String, String>, Double>()

        distances["Toronto" to "Rome"] = 5000.0
        distances["Rome" to "Florence"] = 200.0
        distances["Florence" to "Toronto"] = 5100.0

        val cities = locations(distances.symmetric(), 0.0, Double.MAX_VALUE).map { City(it.token, it) }.associateBy(City<*, *>::name)

        val florence = cities["Florence"]!!
        val toronto = cities["Toronto"]!!
        val rome = cities["Rome"]!!

        assertThat(florence.distanceFrom(florence)).isEqualTo(0.0)
        assertThat(rome.distanceFrom(rome)).isEqualTo(0.0)
        assertThat(toronto.distanceFrom(toronto)).isEqualTo(0.0)

        assertThat(florence.distanceFrom(rome)).isEqualTo(200.0)
        assertThat(rome.distanceFrom(florence)).isEqualTo(200.0)

        assertThat(toronto.distanceFrom(rome)).isEqualTo(5000.0)
        assertThat(rome.distanceFrom(toronto)).isEqualTo(5000.0)

        assertThat(toronto.distanceFrom(florence)).isEqualTo(5100.0)
        assertThat(florence.distanceFrom(toronto)).isEqualTo(5100.0)
    }
}