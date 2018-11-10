package sollecitom.exercises.tsp.domain

import java.util.AbstractMap

internal fun <DISTANCE : Comparable<DISTANCE>, TOKEN> locations(distances: Map<Pair<TOKEN, TOKEN>, DISTANCE>, distanceZero: DISTANCE? = null): List<TokenizedRelativeLocation<DISTANCE, TOKEN>> {
    return distances.map { (pair, _) ->
        val token = pair.first
        val map = distances.filter { it.key.first == token }.mapKeys { entry -> entry.key.second }.toMutableMap()
        distanceZero?.let {
            map += token to distanceZero
        }
        TokenizedRelativeLocation(token, map)
    }
}

internal fun <KEY, VALUE> Map<Pair<KEY, KEY>, VALUE>.symmetric(): Map<Pair<KEY, KEY>, VALUE> {

    val symmetricEntries = entries.map { AbstractMap.SimpleEntry(it.key.second to it.key.first, it.value) }
    val allEntries = entries + symmetricEntries
    return allEntries.map { it.toPair() }.toMap()
}