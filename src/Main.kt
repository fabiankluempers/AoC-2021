fun main() {
	val puzzle = Day04()

	val testInput = readInput("${puzzle.name}_test")
	check(puzzle.part1(testInput) == puzzle.testResultPart1)
	check(puzzle.part2(testInput) == puzzle.testResultPart2)

	val input = readInput(puzzle.name)
	println(puzzle.part1(input))
	println(puzzle.part2(input))
}