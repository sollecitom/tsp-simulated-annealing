package sollecitom.exercises.tsp.domain

fun <ELEMENT> Set<ELEMENT>.permutations(): Sequence<List<ELEMENT>> = (0 until size.factorial()).asSequence().map { i -> permutation(i, this) }

private fun Int.factorial(): Int = (2..this).fold(1) { x, y -> x * y }

private fun <ELEMENT> permutation(count: Int, input: MutableList<ELEMENT>, output: MutableList<ELEMENT>): List<ELEMENT> {
    if (input.isEmpty()) {
        return output
    }
    val factorial = (input.size - 1).factorial()
    output.add(input.removeAt(count / factorial))
    // Recursion here is a bad idea, but it will do for now.
    return permutation(count % factorial, input, output)
}

private fun <ELEMENT> permutation(count: Int, items: Set<ELEMENT>): List<ELEMENT> = permutation(count, items.toMutableList(), mutableListOf())