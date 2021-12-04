abstract class Puzzle(val name: String, val testResultPart1 : Int, val testResultPart2 : Int) {
	abstract fun part1(input : Input) : Int
	abstract fun part2(input : Input) : Int
}