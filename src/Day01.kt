class Day01 : Puzzle<Int>("Day01", 7, 5) {
	override fun part1(input: List<String>) = input
		.map(String::toInt)
		.zipWithNext()
		.count { it.second > it.first }

	override fun part2(input: List<String>) = input
		.asSequence()
		.map(String::toInt)
		.windowed(3, 1)
		.map(List<Int>::sum)
		.zipWithNext()
		.count { it.second > it.first }
}