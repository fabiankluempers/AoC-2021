abstract class Puzzle(val name: String, val testResultPart1 : Int, val testResultPart2 : Int) {
	abstract fun part1(input : List<String>) : Int
	abstract fun part2(input : List<String>) : Int
}