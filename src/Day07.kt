import kotlin.math.absoluteValue
import kotlin.math.roundToInt

class Day07 : Puzzle<Int>("Day07", 37, 168) {
	override fun part1(input: Input): Int = solution(input, false)

	override fun part2(input: Input): Int = solution(input, true)

	private fun solution(input: Input, isToTriangularNumber: Boolean): Int {
		val crabPositions = input.first().splitToInt(',')
		val maxCrabPos = crabPositions.maxOf(::identity)
		return (0..maxCrabPos)
			.map { candidatePos ->
				crabPositions
					.sumOf {
						with((it - candidatePos).absoluteValue) {
							if (isToTriangularNumber) toTriangularNumber() else this
						}
					}
			}
			.minOf(::identity)
	}

	private fun Int.toTriangularNumber() = (this * this + this) / 2
}



