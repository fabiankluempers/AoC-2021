import kotlin.math.min

class Day21 : Puzzle<Int>("Day21", 739785, 0) {
	override fun part1(input: Input): Int {
		val player1 = Player(input[0].last().digitToInt(), 0)
		val player2 = Player(input[1].last().digitToInt(), 0)
		val turnOrder = generateSequence { listOf(player1, player2) }.flatten().iterator()
		val rollResults = generateSequence(0) { it + 1 }
			.map { it % 100 + 1 }
			.chunked(3)
			.map { it.sum() }
			.iterator()
		var numDieRolls = 0
		while (player1.score < 1000 && player2.score < 1000) {
			numDieRolls += 3
			with(turnOrder.next()) {
				pos = (pos + rollResults.next() - 1) % 10 + 1
				score += pos
			}
		}
		return min(player1.score, player2.score) * numDieRolls
	}

	class Player(var pos: Int, var score: Int)

	override fun part2(input: Input): Int {
		return -1
	}
}
