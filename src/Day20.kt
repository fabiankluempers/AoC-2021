class Day20 : Puzzle<Int>("Day20", 0, 0) {
	override fun part1(input: Input): Int = solution(input, 2)

	override fun part2(input: Input): Int = solution(input,50)

	private fun solution(input: Input, iterations: Int): Int {
		if (input.isEmpty()) return -1
		val (dict, imgData) = input.parse()
		val result = enhancementAlgorithm(dict, imgData, iterations)
		return result.sumOf { it.count { char -> char == '1' } }
	}

	private fun enhancementAlgorithm(
		dict: Array<Char>,
		imgData: Array<Array<Char>>,
		iterations: Int
	): Array<Array<Char>> {
		var data = imgData.expandFor(iterations)

		fun Array<Array<Char>>.getSafe(row: Int, col: Int, currentIteration: Int): Char {
			val index = Array2dIndex(row, col)
			return if (index validIndexOf this) {
				this[index]
			} else {
				if (currentIteration % 2 == 0) '0' else '1'
			}
		}
		repeat(iterations) {
			val workingCopy = Array(data.size) { Array(data[0].size) { ' ' } }
			for (row in data.indices) {
				for (col in data[row].indices) {
					workingCopy[row][col] = dict[StringBuilder().apply {
						append(data.getSafe((row - 1), (col - 1), it))
						append(data.getSafe((row - 1), col, it))
						append(data.getSafe((row - 1), (col + 1), it))
						append(data.getSafe(row, (col - 1), it))
						append(data.getSafe(row, col, it))
						append(data.getSafe(row, (col + 1), it))
						append(data.getSafe((row + 1), (col - 1), it))
						append(data.getSafe((row + 1), col, it))
						append(data.getSafe((row + 1), (col + 1), it))
					}.toString().toInt(2)]
				}
			}
			data = workingCopy
		}
		return data
	}

	private fun Array<Array<Char>>.expandFor(iterations: Int): Array<Array<Char>> {
		val expanded = Array(this.size + (2 * iterations)) {
			Array(this[0].size + (2 * iterations)) {
				'0'
			}
		}
		for (row in this.indices) {
			for (col in this[row].indices) {
				expanded[row + iterations][col + iterations] = this[row][col]
			}
		}
		return expanded
	}

	private fun Input.parse(): Pair<Array<Char>, Array<Array<Char>>> {
		return Pair(
			this
				.first()
				.map { it.toBinary() }
				.toTypedArray(),
			this
				.drop(2)
				.map { line -> line.map { it.toBinary() }.toTypedArray() }
				.toTypedArray()
		)
	}

	private fun Char.toBinary() = when (this) {
		'.' -> '0'
		'#' -> '1'
		else -> error("cant convert $this to binary")
	}
}
