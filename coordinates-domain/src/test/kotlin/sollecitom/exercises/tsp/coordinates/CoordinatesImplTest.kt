package sollecitom.exercises.tsp.coordinates

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class CoordinatesImplTest {

    @Test
    fun contract_acceptance() {
        val latitude = Dimension("Latitude", 0..10)
        val longitude = Dimension("Longitude", 0..10)
        val system = Coordinates.System(latitude, longitude)

        val coordinates = system.newCoordinates { coordinates ->
            coordinates[latitude] = 5
            coordinates[longitude] = 10
        }

        assertThat(coordinates.size).isEqualTo(system.size)
        assertThat(coordinates[latitude]).isEqualTo(5)
        assertThat(coordinates[longitude]).isEqualTo(10)
    }

    @Test
    fun out_of_boundaries_values_are_rejected() {
        val latitude = Dimension("Latitude", 0..10)
        val longitude = Dimension("Longitude", 0..10)
        val system = Coordinates.System(latitude, longitude)

        assertThatThrownBy {
            system.newCoordinates { coordinates ->
                coordinates[latitude] = 5
                coordinates[longitude] = longitude.upperBound!!.value + 1
            }
        }.isInstanceOf(IllegalArgumentException::class.java)

        assertThatThrownBy {
            system.newCoordinates { coordinates ->
                coordinates[latitude] = latitude.lowerBound!!.value - 1
                coordinates[longitude] = 6
            }
        }.isInstanceOf(IllegalArgumentException::class.java)
    }
}