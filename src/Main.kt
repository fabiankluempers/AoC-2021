import kotlin.system.measureTimeMillis

fun main() {
	val puzzle = Day08()
	val testInput = readInput("${puzzle.name}_test")
	check(puzzle.part1(testInput) == puzzle.testResultPart1)
	check(puzzle.part2(testInput) == puzzle.testResultPart2)

	val input = readInput(puzzle.name)
	var result : Any
	println("Part 1 of ${puzzle.name} solved in ${measureTimeMillis { result = puzzle.part1(input) }}ms with result: $result")
	println("Part 2 of ${puzzle.name} solved in ${measureTimeMillis { result = puzzle.part2(input) }}ms with result: $result")
}

typealias Input = List<String>