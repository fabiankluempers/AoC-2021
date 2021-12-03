import kotlin.text.StringBuilder

class Day03 : Puzzle("Day03", 198, 230) {
	override fun part1(input: List<String>): Int {
		return part1Solution2(input)
	}

	private fun part1Solution1(input: List<String>): Int {
		val gamma = StringBuilder().apply {
			input.toColumns().forEach { column ->
				val zeroes = column.count { it == '0' }
				val ones = column.length - zeroes
				if (zeroes > ones) this.append('0') else this.append('1')
			}
		}.toString()
		val epsilon = StringBuilder().apply {
			gamma.forEach {
				when (it) {
					'0' -> this.append('1')
					'1' -> this.append('0')
					else -> error("cant flip $it")
				}
			}
		}.toString()
		return gamma.toInt(radix = 2) * epsilon.toInt(radix = 2)
	}

	private fun part1Solution2(input: List<String>): Int {
		val count = Array(input[0].length) { 0 }
		input.forEach {
			for (i in count.indices) {
				count[i] += if (it[i] == '1') 1 else -1
			}
		}
		val gammaBuilder = StringBuilder()
		val epsilonBuilder = StringBuilder()
		count.forEach {
			gammaBuilder.append(if (it > 0) '1' else '0')
			epsilonBuilder.append(if (it > 0) '0' else '1')
		}
		val gamma = gammaBuilder.toString().toInt(radix = 2)
		val epsilon = epsilonBuilder.toString().toInt(radix = 2)
		return gamma * epsilon
	}

	private fun List<String>.toColumns(): List<String> {
		val columns = List(this[0].length) { StringBuilder() }
		return this.map { it.toCharArray() }.fold(columns) { acc, chars ->
			acc.also {
				chars.forEachIndexed { index, char ->
					it[index].append(char)
				}
			}
		}.map(StringBuilder::toString)
	}

	override fun part2(input: List<String>): Int {
		val oxygen = part2Rec(input, 0, true).toInt(radix = 2).also { println(it) }
		val co2 = part2Rec(input, 0, false).toInt(radix = 2).also { println(it) }
		return oxygen * co2
	}

	private fun part2Rec(input: List<String>, depth: Int, dominantBit: Boolean): String {
		if (input.size == 1) return input[0]
		var count = 0
		input.forEach {
			count += if (it[depth] == '1') 1 else -1
		}
		println(count)
		return when {
			count >= 0 -> part2Rec(input.filter { it[depth] == if (dominantBit) '1' else '0' }, depth + 1, dominantBit)
			else -> part2Rec(input.filter { it[depth] == if (!dominantBit) '1' else '0' }, depth + 1, dominantBit)
		}
	}
}
