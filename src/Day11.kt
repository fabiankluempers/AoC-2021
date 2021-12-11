class Day11 : Puzzle<Int>("Day11", 1656, 195) {
	override fun part1(input: Input): Int {
		val octopuses = input.toOctopuses()
		var result = 0
		repeat(100) {
			result += octopuses.step()
		}
		return result
	}

	override fun part2(input: Input): Int {
		val octopuses = input.toOctopuses()
		var numOfSteps = 0
		while (octopuses.anyCell { it != 0 }) {
			octopuses.step()
			numOfSteps++
		}
		return numOfSteps
	}

	private fun Input.toOctopuses() = this
		.map { it.map(Char::digitToInt).toTypedArray() }
		.toTypedArray()

	private fun Array2d<Int>.step(): Int {
		this.update { x -> x + 1 }
		val unevaluated = this.indicesWhere { it > 9 }.toMutableSet()
		val evaluated = mutableSetOf<Array2dIndex>()
		while (unevaluated.isNotEmpty()) {
			val index = unevaluated.first()
			val adjacent = index.getAdjacentIndices().filter { it validIndexOf this }
			adjacent.forEach { this.updateAt(it) { x -> x + 1 } }
			evaluated += index
			unevaluated += (adjacent.filter { this[it] > 9 })
			unevaluated -= evaluated
		}
		val flashing = this.indicesWhere { it > 9 }
		for (index in flashing) {
			this[index] = 0
		}
		return flashing.size
	}

}