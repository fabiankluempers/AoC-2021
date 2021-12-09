class Day09 : Puzzle<Int>("Day09", 15, 1134) {
	override fun part1(input: Input): Int {
		val caveData = input.map { it.map(Char::digitToInt) }
		val lowPoints = caveData.lowPoints()
		return lowPoints.sumOf { caveData[it.first][it.second] + 1 }
	}

	private fun List<List<Int>>.lowPoints(): List<Pair<Int, Int>> {
		val lowPoints = mutableListOf<Pair<Int, Int>>()

		for (x in this.indices) {
			for (y in this[x].indices) {
				if (this.isLowPoint(x, y)) lowPoints.add(Pair(x, y))
			}
		}

		return lowPoints
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
		val caveData = input.map { it.map(Char::digitToInt) }
		val lowPoints = caveData.lowPoints()
		val nullableCaveData = Array(caveData.size) {
			caveData[it].toTypedArray<Int?>()
		}
		val basinSizes = mutableListOf<Int>()
		for (lowPoint in lowPoints) {
			basinSizes += nullableCaveData.discoverBasin(lowPoint.first, lowPoint.second)
		}
		return basinSizes.sorted().takeLast(3).reduce { x, y -> x * y }
	}

	private fun Array<Array<Int?>>.discoverBasin(x: Int, y: Int): Int =
		if (x !in this.indices || y !in this[x].indices || this[x][y] == null || this[x][y] == 9 )
			0
		else {
			this[x][y] = null
			listOf(
				discoverBasin((x + 1), y),
				discoverBasin(x, (y - 1)),
				discoverBasin(x, (y + 1)),
				discoverBasin((x - 1), y),
			).sum() + 1
		}

}
