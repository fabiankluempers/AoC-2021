class Day04 : Puzzle("Day04", 4512 , 1924) {
	override fun part1(input: Input): Int {
		val drawnNumbers = input.getDrawnNumbers()
		val boards = input.getBoards()

		for (i in drawnNumbers.indices) {
			boards.forEach { board ->
				if (board.markAndIsWin(drawnNumbers[i])) {
					return board.calculateFinalScore(drawnNumbers[i])
				}
			}
		}
		error("Could not determine a Winner")
	}

	private fun Input.getDrawnNumbers() = this[0].trim().split(',').map(String::toInt)

	private fun Input.getBoards() = this.drop(2).filter(String::isNotBlank).chunked(5).map {  it.toBoard() }

	private fun List<String>.toBoard() : Array<Array<Pair<Int, Boolean>>> = this.map { line ->
		line.trim().split(Regex("""\s+""")).map { Pair(it.toInt(), false) }.toTypedArray()
	}.toTypedArray()

	private fun Array<Array<Pair<Int, Boolean>>>.markAndIsWin(drawnNumber : Int) : Boolean {
		for (x in this.indices) {
			for (y in this[x].indices) {
				val cell = this[x][y]
				if (cell.first == drawnNumber) {
					this[x][y] = cell.copy(second = true)
					val checkRow = (this[x].all { it.second })
					val checkColumn = this.map { it[y] }.all { it.second }
					return checkRow || checkColumn
				}
			}
		}
		return false
	}

	private fun Array<Array<Pair<Int, Boolean>>>.calculateFinalScore(winningNumber: Int) =
		this.flatMap { row -> row.filter { !it.second } }.sumOf(Pair<Int, Boolean>::first) * winningNumber

	override fun part2(input: Input): Int {
		val drawnNumbers = input.getDrawnNumbers()
		val boards = input.getBoards()
		val candidates = boards.toMutableList()
		var drawnNumberIndex = 0
		while (candidates.size > 1) {
			candidates.removeIf { it.markAndIsWin(drawnNumbers[drawnNumberIndex]) }
			drawnNumberIndex++
		}
		while (!candidates[0].markAndIsWin(drawnNumbers[drawnNumberIndex])) drawnNumberIndex++
		return candidates[0].calculateFinalScore(drawnNumbers[drawnNumberIndex])
	}
}
