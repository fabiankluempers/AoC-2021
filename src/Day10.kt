class Day10 : Puzzle<Long>("Day10", 26397, 288957) {
	override fun part1(input: Input): Long = input
		.mapNotNull { analyzeLine(it).first.firstOrNull() }
		.sumOf { it.pointsForIllegal() }
		.toLong()

	/**
	 * Returns all incorrect closing symbols in [line] as first and
	 * all closing symbols that need to be added to complete the [line] as second.
	 */
	private fun analyzeLine(line: String): Pair<List<Char>, List<Char>> {
		val stack = ArrayDeque<Char>(line.length)
		val falseClosing = mutableListOf<Char>()
		for (char in line) {
			if (char.isOpeningSymbol()) {
				stack.add(char)
			}
			if (char.isClosingSymbol()) {
				if (stack.last() != char.flipStructureSymbol()) {
					falseClosing.add(char)
				}
				stack.removeLast()
			}
		}
		return Pair(falseClosing, stack.map { it.flipStructureSymbol() }.reversed())
	}

	private fun Char.isOpeningSymbol() = when (this) {
		in listOf('(', '[', '{', '<') -> true
		in listOf(')', ']', '}', '>') -> false
		else -> error("$this is not a structure symbol")
	}

	private fun Char.isClosingSymbol() = !this.isOpeningSymbol()

	private fun Char.flipStructureSymbol() = when (this) {
		'(' -> ')'
		')' -> '('
		'[' -> ']'
		']' -> '['
		'{' -> '}'
		'}' -> '{'
		'<' -> '>'
		'>' -> '<'
		else -> error("cant reverse because $this is not a structure symbol")
	}

	private fun Char.pointsForIllegal() = when (this) {
		')' -> 3
		']' -> 57
		'}' -> 1197
		'>' -> 25137
		else -> error("toPoint is undefined for $this")
	}

	private fun Char.pointsForFixed() = when (this) {
		')' -> 1
		']' -> 2
		'}' -> 3
		'>' -> 4
		else -> error("toPoint is undefined for $this")
	}

	private fun calculateScoreForFixed(list: List<Char>) = list
		.map { it.pointsForFixed() }
		.fold(0L) { acc, i -> acc * 5 + i }

	override fun part2(input: Input): Long = input
		.map { analyzeLine(it) }
		.filter { it.first.isEmpty() }
		.map { calculateScoreForFixed(it.second) }
		.sorted()
		.let { it[it.size / 2] }
}