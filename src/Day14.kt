class Day14 : Puzzle<Long>("Day14", 1588, 2188189693529) {
	override fun part1(input: Input): Long {
		val ruleMap = input
			.takeLastWhile(String::isNotBlank)
			.map { it.split(" -> ") }
			.associate { Pair(it.component1(), it.component2()) }

		var result = input.first()
		repeat(10) {
			result = result.applyInsertions(ruleMap)
		}
		val occurrences = result.groupingBy(::identity).eachCount()
		return (occurrences.maxOf { it.value } - occurrences.minOf { it.value }).toLong()
	}

	private fun String.applyInsertions(rules: Map<String, String>) = this
		.zipWithNext()
		.joinToString(separator = "", postfix = takeLast(1)) { "${it.first}${rules["${it.first}${it.second}"]}" }

	override fun part2(input: Input): Long {
		val ruleMap = input
			.takeLastWhile(String::isNotBlank)
			.map { it.split(" -> ") }
			.associate { Pair(Pair(it.component1()[0], it.component1()[1]), it.component2().first()) }

		val pairCounts = ruleMap.keys.associateWith { 0L }.toMutableMap()

		input.first().zipWithNext().forEach {
			pairCounts[it] = pairCounts[it]!! + 1
		}

		repeat(40) {
			val temp = ruleMap.keys.associateWith { 0L }.toMutableMap()
			for (entry in pairCounts) {
				val (headLeft, headRight) = entry.key
				val body = ruleMap[entry.key]!!
				temp[Pair(headLeft, body)] = temp[Pair(headLeft, body)]!! + entry.value
				temp[Pair(body, headRight)] = temp[Pair(body, headRight)]!! + entry.value
				temp[entry.key] = temp[entry.key]!! - entry.value
			}
			for (entry in temp) {
				pairCounts[entry.key] = pairCounts[entry.key]!! + entry.value
			}
		}
		val charCounts = mutableMapOf<Char, Long>()
		for (entry in pairCounts) {
			val (left, right) = entry.key
			charCounts[left] = charCounts[left]?.plus(entry.value) ?: entry.value
			charCounts[right] = charCounts[right]?.plus(entry.value) ?: entry.value
		}
		val (max, min) = with(charCounts.mapValues { ((it.value + 1) / 2) }) {
			Pair(maxOf { it.value }, minOf { it.value })
		}
		return max - min
	}
}