import kotlin.math.absoluteValue

class Day07 : Puzzle<Int>("Day07", 37 , 168) {
	override fun part1(input: Input): Int {
		val crabPositions = input.first().split(',').map(String::toInt)
		val rightMostPos = crabPositions.maxOf { it }
		return (0 .. rightMostPos).mapIndexed { index, _ ->
			crabPositions.sumOf { (it - index).absoluteValue }
		}.minOf { it }
	}

	override fun part2(input: Input): Int {
		val crabPositions = input.first().split(',').map(String::toInt)
		val rightMostPos = crabPositions.maxOf { it }
		return (0 .. rightMostPos).mapIndexed { index, _ ->
			crabPositions.sumOf { (it - index).absoluteValue.toTriangularNumber() }
		}.minOf { it }
	}

	fun Int.toTriangularNumber() = (this*this + this)/2
}