class Day09 : Puzzle<Int>("Day09", 15, 0) {
	override fun part1(input: Input): Int {
		val caveData = input.map { it.map(Char::digitToInt) }
		val lowPoints = mutableListOf<Int>()

		for (x in caveData.indices) {
			for (y in caveData[x].indices) {
				if (caveData.isLowPoint(x, y)) lowPoints.add(caveData[x][y])
			}
		}

		return lowPoints.sumOf { it + 1 }
	}

	private fun List<List<Int>>.isLowPoint(x: Int, y: Int): Boolean = listOfNotNull(
		safeAccess((x + 1), y),
		safeAccess((x - 1), y),
		safeAccess(x, (y + 1)),
		safeAccess(x, (y - 1)),
	).all { it > this[x][y] }

	private fun List<List<Int>>.safeAccess(x: Int, y: Int): Int? =
		if (x in this.indices && y in this[x].indices) this[x][y] else null

	override fun part2(input: Input): Int {
		return 0
	}
}
