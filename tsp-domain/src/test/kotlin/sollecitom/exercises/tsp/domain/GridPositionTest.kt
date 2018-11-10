package sollecitom.exercises.tsp.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class GridPositionTest {

    @Nested
    inner class Distance {

        private val grid = Grid(-10..10, -10..10)

        @Test
        fun horizontal() {
            val origin = grid.locationAt(0, 0)
            val oneZero = grid.locationAt(1, 0)
            val minusOneZero = grid.locationAt(-1, 0)

            assertThat(origin.distanceFrom(origin)).isEqualTo(0.0)

            assertThat(origin.distanceFrom(oneZero)).isEqualTo(1.0)
            assertThat(oneZero.distanceFrom(origin)).isEqualTo(1.0)

            assertThat(origin.distanceFrom(minusOneZero)).isEqualTo(1.0)
            assertThat(minusOneZero.distanceFrom(origin)).isEqualTo(1.0)

            assertThat(oneZero.distanceFrom(minusOneZero)).isEqualTo(2.0)
            assertThat(minusOneZero.distanceFrom(oneZero)).isEqualTo(2.0)
        }

        @Test
        fun vertical() {
            val origin = grid.locationAt(0, 0)
            val zeroOne = grid.locationAt(0, 1)
            val zeroMinusOne = grid.locationAt(0, -1)

            assertThat(origin.distanceFrom(origin)).isEqualTo(0.0)

            assertThat(origin.distanceFrom(zeroOne)).isEqualTo(1.0)
            assertThat(zeroOne.distanceFrom(origin)).isEqualTo(1.0)

            assertThat(origin.distanceFrom(zeroMinusOne)).isEqualTo(1.0)
            assertThat(zeroMinusOne.distanceFrom(origin)).isEqualTo(1.0)

            assertThat(zeroOne.distanceFrom(zeroMinusOne)).isEqualTo(2.0)
            assertThat(zeroMinusOne.distanceFrom(zeroOne)).isEqualTo(2.0)
        }
    }
}