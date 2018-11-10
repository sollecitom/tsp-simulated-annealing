package sollecitom.exercises.tsp.domain

import sollecitom.exercises.tsp.coordinates.Coordinates
import sollecitom.exercises.tsp.coordinates.Dimension
import kotlin.math.sqrt

class Grid(xRange: ClosedRange<Int>, yRange: ClosedRange<Int>) {

    val xDimension = Dimension("X", xRange)
    val yDimension = Dimension("Y", yRange)
    private val coordinatesSystem = Coordinates.System(xDimension, yDimension)

    fun locationAt(x: Int, y: Int): Grid.Position {
        val coordinates = coordinatesSystem.newCoordinates { coordinates ->
            coordinates[xDimension] = x
            coordinates[yDimension] = y
        }
        return locationAt(coordinates)
    }

    fun locationAt(coordinates: Coordinates): Grid.Position {
        require(coordinates in coordinatesSystem)
        return Grid.Position(coordinates[xDimension], coordinates[yDimension])
    }

    data class Position(val x: Int, val y: Int) : Distant<Double, Position> {

        override fun distanceFrom(other: Position) = sqrt(Math.pow(Math.abs(x - other.x).toDouble(), 2.0) + Math.pow(Math.abs(y - other.y).toDouble(), 2.0))
    }
}