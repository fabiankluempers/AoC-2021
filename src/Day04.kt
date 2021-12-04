class Day04 : Puzzle("Day04", 4512 , 1924) {
	private fun Input.getDrawnNumbers() = this[0].trim().split(',').map(String::toInt)

	private fun Input.getBoards() = this.drop(2).filter(String::isNotBlank).chunked(5).map {  it.toBoard() }

	private fun Input.toBoard() : BingoBoard = this.map { line ->
		line.trim().split(Regex("""\s+""")).map { Pair(it.toInt(), false) }.toTypedArray()
	}.toTypedArray()

	private fun BingoBoard.markAndIsWin(drawnNumber : Int) : Boolean {
		for (x in this.indices) {
			for (y in this[x].indices) {
				val cell = this[x][y]
				if (cell.first == drawnNumber) {
					this[x][y] = cell.copy(second = true)
					val checkRow = this[x].all { it.second }
					val checkColumn = this.all { it[y].second }
					return checkRow || checkColumn
				}
			}
		}
		return false
	}

	private fun BingoBoard.sumOfUnmarked() =
		this.flatMap { row -> row.filter { !it.second } }.sumOf(Pair<Int, Boolean>::first)

	override fun part1(input: Input): Int {
		val drawnNumbers = input.getDrawnNumbers()
		val boards = input.getBoards()

		for (i in drawnNumbers.indices) {
			boards.forEach { board ->
				if (board.markAndIsWin(drawnNumbers[i])) {
					return board.sumOfUnmarked() * drawnNumbers[i]
				}
			}
		}

		error("Could not determine a Winner")
	}

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

		return candidates[0].sumOfUnmarked() * drawnNumbers[drawnNumberIndex]
	}
}

typealias BingoBoard = Array<Array<Pair<Int, Boolean>>>
