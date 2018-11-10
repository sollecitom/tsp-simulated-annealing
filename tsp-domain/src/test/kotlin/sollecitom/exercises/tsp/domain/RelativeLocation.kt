package sollecitom.exercises.tsp.domain

class TokenizedRelativeLocation<DISTANCE : Comparable<DISTANCE>, TOKEN>(val token: TOKEN, private val distancesByOther: Map<TOKEN, DISTANCE>) : Distant<DISTANCE, TokenizedRelativeLocation<DISTANCE, TOKEN>> {

    override fun distanceFrom(other: TokenizedRelativeLocation<DISTANCE, TOKEN>): DISTANCE = distancesByOther[other.token]!!
}