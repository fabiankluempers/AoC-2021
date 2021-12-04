import java.util.regex.Pattern

class Day04 : Puzzle("Day04", 4512 , 0) {
	override fun part1(input: List<String>): Int {
		val drawnNumbers = input[0].trim().split(',').map(String::toInt)
		val boards = input.drop(2).filter(String::isNotBlank).chunked(5).map {  it.toBoard() }

		for (i in drawnNumbers.indices) {
			boards.forEach { board ->
				if (board.mark(drawnNumbers[i])) {
					return board.flatMap { row ->
						row.filter {
							!it.second
						} }.sumOf(Pair<Int, Boolean>::first) * drawnNumbers[i]
				}
			}
		}
		return 0
	}

	private fun List<String>.toBoard() : Array<Array<Pair<Int, Boolean>>> = this.map { line ->
		line.trim().split(Regex("""\s+""")).map { Pair(it.toInt(), false) }.toTypedArray()
	}.toTypedArray()

	private fun Array<Array<Pair<Int, Boolean>>>.mark(drawnNumber : Int) : Boolean {
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


	override fun part2(input: List<String>): Int {
		return 0
	}
}
