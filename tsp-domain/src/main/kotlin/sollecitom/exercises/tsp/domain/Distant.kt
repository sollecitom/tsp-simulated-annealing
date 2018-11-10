package sollecitom.exercises.tsp.domain

// TODO move to a location module
interface Distant<DISTANCE : Comparable<DISTANCE>, SELF : Distant<DISTANCE, SELF>> {

    fun distanceFrom(other: SELF): DISTANCE
}