class Day13 : Puzzle<Int>("Day13", 17, 0) {
	sealed class FoldInstruction {
		abstract fun foldAccordingly(points: MutableSet<Point>)
	}

	data class FoldVer(val index: Int) : FoldInstruction() {
		override fun foldAccordingly(points: MutableSet<Point>) {
			for (point in points.filter { it.x > this.index }) {
				points.remove(point)
				points.add(point.copy(x = (2 * this.index - point.x)))
			}
		}
	}

	data class FoldHor(val index: Int) : FoldInstruction() {
		override fun foldAccordingly(points: MutableSet<Point>) {
			for (point in points.filter { it.y > this.index }) {
				points.remove(point)
				points.add(point.copy(y = (2 * this.index - point.y)))
			}
		}
	}

	private fun parseFoldInstruction(string: String): FoldInstruction {
		val (left, right) = string.split('=')
		return when (left.last()) {
			'x' -> FoldVer(right.toInt())
			'y' -> FoldHor(right.toInt())
			else -> error("could not read fold instruction $left")
		}
	}

	private fun Input.generateInstructions() = this
		.takeLastWhile { it.isNotBlank() }
		.map { parseFoldInstruction(it) }

	private fun foldInputSheet(input: Input, instructions: List<FoldInstruction>): Set<Point> {
		val points = input
			.takeWhile { it.isNotBlank() }
			.map { with(it.splitToInt(',')) { Point(x = component1(), y = component2()) } }.toMutableSet()
		for (instruction in instructions) { instruction.foldAccordingly(points) }
		return points
	}

	override fun part1(input: Input): Int = foldInputSheet(input, input.generateInstructions().subList(0, 1)).count()

	override fun part2(input: Input): Int {
		val points = foldInputSheet(input, input.generateInstructions())
		val maxX = points.maxOf { it.x }
		val maxY = points.maxOf { it.y }
		val result = Array(maxY + 1) { Array(maxX + 1) { "  " } }
		for (point in points) {
			result[point.y][point.x] = "||"
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