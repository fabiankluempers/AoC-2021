import kotlin.math.min

class Day21 : Puzzle<Int>("Day21", 739785, 0) {
	override fun part1(input: Input): Int {
		val player1 = Player(input[0].last().digitToInt(), 0, "player1")
		val player2 = Player(input[1].last().digitToInt(), 0, "player2")
		val turnOrder = generateSequence { listOf(player1, player2) }.flatten().iterator()
		var lastDieRoll = 0
		var numDieRolls = 0
		while (player1.score < 1000 && player2.score < 1000) {
			val (rollResult, last) = rollDetDie(lastDieRoll)
			lastDieRoll = last
			numDieRolls += 3
			with(turnOrder.next()) {
				pos = (pos + rollResult - 1) % 10 + 1
				score += pos
			}
		}
		return min(player1.score, player2.score) * numDieRolls
	}

	class Player(var pos: Int, var score: Int, val name: String)

	private fun rollDetDie(prev: Int) = with(listOf(0, 1, 2).map { (it + prev).incWrapping(100) }) { sum() to last() }

	private fun Int.incWrapping(wrappingAt: Int) = (this % wrappingAt) + 1

	override fun part2(input: Input): Int {
		return -1
	}
}
