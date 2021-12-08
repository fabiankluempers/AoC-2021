class Day08 : Puzzle<Int>("Day08", 26, 0) {

	//This is not very useful for solving the actual problem but it solves part1
	override fun part1(input: Input): Int {
		val outputValues = input.map { it.split('|')[1].trim().split(' ') }
		val occurrencesOfLength = outputValues
			.flatten()
			.groupingBy { it.length }
			.eachCount()
		return listOfNotNull(
			occurrencesOfLength[2],
			occurrencesOfLength[4],
			occurrencesOfLength[3],
			occurrencesOfLength[7],
		).sum()
	}

	override fun part2(input: Input): Int {
		return 0
	}
}