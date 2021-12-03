import kotlin.system.measureTimeMillis
import kotlin.text.StringBuilder

class Day03 : Puzzle("Day03", 198, 0) {
	override fun part1(input: List<String>): Int {
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
		val gamma = gammaBuilder.toString()
		val epsilon = epsilonBuilder.toString()
		return gamma.toInt(radix = 2) * epsilon.toInt(radix = 2)
	}

	override fun part2(input: List<String>): Int {
		return 0
	}
}