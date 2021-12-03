class Day02 : Puzzle("Day02", 150, 900) {
	override fun part1(input: List<String>) = input
		.fold(Pair(0,0)) { (horizontal, vertical), string ->
			val (direction, delta) = splitInput(string)
			when (direction) {
				"forward" -> Pair(horizontal + delta, vertical)
				"down" -> Pair(horizontal, vertical + delta)
				"up" -> Pair(horizontal, vertical - delta)
				else -> error("$direction is an unknown input")
			}
		}.let { it.first * it.second }

	override fun part2(input: List<String>)= input
		.fold(Triple(0, 0, 0)) { (horizontal, vertical, aim), string ->
			val (direction, delta) = splitInput(string)
			when (direction) {
				"forward" -> Triple(horizontal + delta, vertical + (aim * delta), aim)
				"down" -> Triple(horizontal, vertical, (aim + delta))
				"up" -> Triple(horizontal, vertical, (aim - delta))
				else -> error("$direction is an unknown input")
			}
		}.let { it.first * it.second }

	private fun splitInput(input: String) = with(input.split(' ')) { Pair(this[0], this[1].toInt()) }
}

