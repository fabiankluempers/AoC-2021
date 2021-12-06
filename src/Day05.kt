class Day05 : Puzzle<Int>("Day05", 5, 12) {

	private fun Input.toLines(): List<Line> = this.map { line ->
		val points = line.split(" -> ").map {
			val values = it.split(',')
			Point(x = values[0].toInt(), y = values[1].toInt())
		}
		Line(points[0], points[1])
	}

	//This solution relies heavily on the fact, that the input contains ONLY vertical, horizontal and diagonal lines.
	//It will result in an infinite sequence generation otherwise.
	//This solution can be improved by using a canonical representation (only from left to right and top to bottom),
	//which results in less cases.
	//Additionally a bounded range should be used to calculate all points of the diagonal lines.
	private fun Line.expandLine(): List<Point> = when {
		first.x == second.x -> //horizontal
			(if (first.y <= second.y) (first.y..second.y) else (second.y..first.y)).map { Point(first.x, it) }
		first.y == second.y -> //vertical
			(if (first.x <= second.x) (first.x..second.x) else (second.x..first.x)).map { Point(it, first.y) }
		first.x < second.x && first.y < second.y -> //diagonal ascending from left
			generateSequence(first) { Point(it.x + 1, it.y + 1) }.takeWhile { it != second }.plus(second).toList()
		first.x < second.x && first.y > second.y -> //diagonal ascending from right
			generateSequence(first) { Point(it.x + 1, it.y - 1) }.takeWhile { it != second }.plus(second).toList()
		first.x > second.x && first.y < second.y -> //diagonal descending from left
			generateSequence(first) { Point(it.x - 1, it.y + 1) }.takeWhile { it != second }.plus(second).toList()
		first.x > second.x && first.y > second.y -> //diagonal descending from right
			generateSequence(first) { Point(it.x - 1, it.y - 1) }.takeWhile { it != second }.plus(second).toList()
		else -> error("$this is not a horizontal, vertical or diagonal line") //would be infinite loop anyways
	}

	private fun List<Line>.calculateArrBounds() = Pair(
		maxOf { maxOf(it.first.x, it.second.x) } + 1,
		maxOf { maxOf(it.first.y, it.second.y) } + 1
	)

	private fun solution(input: Input, filterDiagonals : Boolean) : Int {
		val lines = if (filterDiagonals) {
			input.toLines().filter { it.first.x == it.second.x || it.first.y == it.second.y }
		} else input.toLines()
		val arrBounds = lines.calculateArrBounds()
		val arr = Array(arrBounds.first) {
			Array(arrBounds.second) { 0 }
		}
		lines.forEach { line ->
			line.expandLine().forEach {
				arr[it.x][it.y]++
			}
		}
		return arr.sumOf { it.count { value -> value > 1 } }
	}

	override fun part1(input: Input): Int = solution(input, filterDiagonals = true)

	override fun part2(input: Input): Int = solution(input, filterDiagonals = false)

	data class Point(val x: Int, val y: Int)
}

typealias Line = Pair<Day05.Point, Day05.Point>