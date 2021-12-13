class Day13 : Puzzle<Int>("Day13", 17, 0) {
	override fun part1(input: Input): Int = foldInputSheet(input, input.generateInstructions().subList(0, 1)).count()

	private fun Input.generateInstructions() = this
		.takeLastWhile { it.isNotBlank() }
		.map { readFoldInstruction(it) }

	private fun foldInputSheet(input: Input, instructions: List<FoldInstruction>): Set<Point> {
		val points = input
			.takeWhile { it.isNotBlank() }
			.map { with(it.splitToInt(',')) { Point(x = component1(), y = component2()) } }.toMutableSet()
		for (instruction in instructions) {
			when (instruction) {
				is FoldX -> {
					for (point in points.filter { it.x > instruction.index }) {
						points.remove(point)
						points.add(point.copy(x = (2 * instruction.index - point.x)))
					}
				}
				is FoldY -> {
					for (point in points.filter { it.y > instruction.index }) {
						points.remove(point)
						points.add(point.copy(y = (2 * instruction.index - point.y)))
					}
				}
			}
		}
		return points
	}

	sealed class FoldInstruction()

	data class FoldX(val index: Int) : FoldInstruction()

	data class FoldY(val index: Int) : FoldInstruction()

	private fun readFoldInstruction(string: String): FoldInstruction {
		val (left, right) = string.split('=')
		return when (left.last()) {
			'x' -> FoldX(right.toInt())
			'y' -> FoldY(right.toInt())
			else -> error("could not read fold instruction")
		}
	}

	override fun part2(input: Input): Int {
		val points = foldInputSheet(input, input.generateInstructions())
		val maxX = points.maxOf { it.x }
		val maxY = points.maxOf { it.y }
		val result = Array(maxY + 1) { Array(maxX + 1) { '.' } }
		for (point in points) {
			result[point.y][point.x] = '#'
		}
		val resultString = StringBuilder()
		resultString.appendLine()
		for (y in result.indices) {
			for (x in result[y].indices) {
				resultString.append(result[y][x])
			}
			resultString.appendLine()
		}
		println(resultString)
		return 0
	}
}