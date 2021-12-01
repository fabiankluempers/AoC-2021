fun main() {
	fun part1(input: List<Int>) = input
		.zipWithNext()
		.count { it.second > it.first }

	fun part2(input: List<Int>) = input
		.windowed(3, 1)
		.map(List<Int>::sum)
		.zipWithNext()
		.count { it.second > it.first }

	// test if implementation meets criteria from the description, like:
	val testInput = readInput("Day01_test").map(String::toInt)
	check(part1(testInput) == 7)
	check(part2(testInput) == 5)

	val input = readInput("Day01").map(String::toInt)
	println(part1(input))
	println(part2(input))
}
