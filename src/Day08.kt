class Day08 : Puzzle<Int>("Day08", 26, 61229) {
	private val uniqueLengths = listOf(2, 3, 4, 7)

	override fun part1(input: Input): Int = input
		.flatMap { it.split(" | ")[1].split(' ') }
		.count { it.length in uniqueLengths }

	override fun part2(input: Input): Int = input.sumOf(::solveLine)

	// This works but it is really not pretty. I might optimize, if i find time.
	private fun solveLine(line: String): Int {
		val digitToSignal = mutableMapOf<Int, Set<Char>>()

		val (input, output) = line
			.split(" | ")
			.map { it.split(' ').map(String::toSet) }

		val signalsByLength = input
			.groupBy(Set<Char>::size)
			.mapValues { it.value.toMutableList() }

		// Map trivial digits.
		listOf(1, 7, 4, 8).forEachIndexed { index: Int, i: Int ->
			digitToSignal[i] = signalsByLength[uniqueLengths[index]]!!.first()
		}

		// Deduces the signal for the digit, adds it to digitToSignal map and removes it from the receiver.
		fun MutableList<Set<Char>>.deduce(digit: Int, usingDigit: Int) {
			val signal = digitToSignal[usingDigit]!!
			find { if (it.size < signal.size) signal.containsAll(it) else it.containsAll(signal) }.also {
				requireNotNull(it)
				digitToSignal[digit] = it
				remove(it)
			}
		}

		// Deduce all digits of length 6.
		val candidatesOfLength6 = signalsByLength[6]!!
		candidatesOfLength6.deduce(9, 4)
		candidatesOfLength6.deduce(0, 7)
		digitToSignal[6] = candidatesOfLength6.first()

		// Deduce all digits of length 5.
		val candidatesOfLength5 = signalsByLength[5]!!
		candidatesOfLength5.deduce(3, 1)
		candidatesOfLength5.deduce(5, 6)
		digitToSignal[2] = candidatesOfLength5.first()

		val signalToString = digitToSignal.map { Pair(it.value, it.key.toString()) }.toMap()

		return output.joinToString(separator = "") { signalToString[it.toSet()]!! }.toInt()
	}
}