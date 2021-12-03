class Day02 : Puzzle("Day02", 150, 900) {
	override fun part1(input: List<String>) = input
		.map { it.split(' ') }
		.map { it[0].toPos(it[1].toInt()) }
		.fold(Pair(0, 0)) { acc, pair -> acc + pair }
		.let { it.first * it.second }

	override fun part2(input: List<String>)= input
		.fold(Triple(0, 0, 0)) { (horizontal, vertical, aim), string ->
			val (direction, delta) = string.split(' ').let { Pair(it[0], it[1].toInt()) }
			when (direction) {
				"forward" -> Triple(horizontal + delta, vertical + (aim * delta), aim)
				"down" -> Triple(horizontal, vertical, (aim + delta))
				"up" -> Triple(horizontal, vertical, (aim - delta))
				else -> throw IllegalArgumentException()
			}
		}.let { it.first * it.second }

	private operator fun Pair<Int, Int>.plus(pair: Pair<Int, Int>): Pair<Int, Int> =
		Pair(this.first + pair.first, this.second + pair.second)

	private fun String.toPos(delta: Int): Pair<Int, Int> = when (this) {
		"forward" -> Pair(delta, 0)
		"down" -> Pair(0, delta)
		"up" -> Pair(0, -delta)
		else -> throw IllegalArgumentException()
	}
}

