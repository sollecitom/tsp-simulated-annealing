package sollecitom.exercises.tsp.domain

import sollecitom.exercises.tsp.coordinates.Coordinates
import sollecitom.exercises.tsp.coordinates.Dimension
import java.lang.Math.abs
import java.lang.Math.pow
import kotlin.math.sqrt

// TODO move to a location module
interface Distant<DISTANCE : Comparable<DISTANCE>, SELF : Distant<DISTANCE, SELF>> {

    fun distanceFrom(other: SELF): DISTANCE
}

class City<LOCATION : Distant<Double, LOCATION>>(val name: String, val location: LOCATION) : Distant<Double, City<LOCATION>> {

    override fun distanceFrom(other: City<LOCATION>) = location.distanceFrom(other.location)
}

// TODO move to test
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

        override fun distanceFrom(other: Position) = sqrt(pow(abs(x - other.x).toDouble(), 2.0) + pow(abs(y - other.y).toDouble(), 2.0))
    }
}