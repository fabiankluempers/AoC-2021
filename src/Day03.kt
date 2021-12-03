import kotlin.text.StringBuilder

class Day03 : Puzzle("Day03", 198, 0) {
	override fun part1(input: List<String>): Int {
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
		return 0
	}
}