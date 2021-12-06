abstract class Puzzle<T>(val name: String, val testResultPart1 : T, val testResultPart2 : T) {
	abstract fun part1(input : Input) : T
	abstract fun part2(input : Input) : T
}