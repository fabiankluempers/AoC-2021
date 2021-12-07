import kotlin.math.absoluteValue

class Day07 : Puzzle<Int>("Day07", 37 , 168) {
	override fun part1(input: Input): Int = solution(input) { x, y -> (x - y).absoluteValue }

	override fun part2(input: Input): Int = solution(input) { x, y -> (x - y).absoluteValue.toTriangularNumber() }

	private fun solution(input: Input, distanceFunc: (Int, Int) -> Int) : Int {
		val crabPositions = input.first().splitToInt(',')
		return (0 .. crabPositions.maxOf { it }).mapIndexed { index, _ ->
			crabPositions.sumOf { distanceFunc(it, index) }
		}.minOf { it }
	}

	private fun Int.toTriangularNumber() = (this * this + this) / 2
}