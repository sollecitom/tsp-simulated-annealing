package sollecitom.exercises.tsp.domain

class RelativeLocation<DISTANCE : Comparable<DISTANCE>>(private val distancesByOther: Map<RelativeLocation<DISTANCE>, DISTANCE>) : Distant<DISTANCE, RelativeLocation<DISTANCE>> {

    override fun distanceFrom(other: RelativeLocation<DISTANCE>): DISTANCE = distancesByOther[other]!!
}

class TokenizedRelativeLocation<DISTANCE : Comparable<DISTANCE>, TOKEN>(val token: TOKEN, private val distancesByOther: Map<TOKEN, DISTANCE>) : Distant<DISTANCE, TokenizedRelativeLocation<DISTANCE, TOKEN>> {

    override fun distanceFrom(other: TokenizedRelativeLocation<DISTANCE, TOKEN>): DISTANCE = distancesByOther[other.token]!!
}