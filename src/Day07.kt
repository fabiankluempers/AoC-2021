import kotlin.math.absoluteValue

class Day07 : Puzzle<Int>("Day07", 37 , 168) {
	override fun part1(input: Input): Int = solution(input, false)

	override fun part2(input: Input): Int = solution(input, true)

	private fun solution(input: Input, isToTriangularNumber: Boolean) : Int {
		val crabPositions = input.first().splitToInt(',')
		return (0 .. crabPositions.maxOf { it }).mapIndexed { index, _ ->
			crabPositions.sumOf {
				with((it - index).absoluteValue) { if (isToTriangularNumber) toTriangularNumber() else this }
			}
		}.minOf { it }
	}

	private fun Int.toTriangularNumber() = (this * this + this) / 2
}



