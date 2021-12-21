import kotlin.math.*

class Day21 : Puzzle<Long>("Day21", 739785, 444356092776315) {
	private val perms = mapOf(
		3 to 1,
		4 to 3,
		5 to 6,
		6 to 7,
		7 to 6,
		8 to 3,
		9 to 1,
	)

	override fun part1(input: Input): Long {
		val player1 = MutablePlayer(input[0].last().digitToInt(), 0)
		val player2 = MutablePlayer(input[1].last().digitToInt(), 0)
		val turnOrder = generateSequence { listOf(player1, player2) }.flatten().iterator()
		val rollResults = generateSequence { 1..100 }
			.flatten()
			.chunked(3)
			.map { it.sum() }
			.iterator()
		var numDieRolls = 0
		while (player1.score < 1000 && player2.score < 1000) {
			with(turnOrder.next()) {
				pos = (pos + rollResults.next().also { numDieRolls += 3 } - 1) % 10 + 1
				score += pos
			}
		}
		return (min(player1.score, player2.score) * numDieRolls).toLong()
	}
	data class MutablePlayer(var pos: Int, var score: Int)

	data class Player(val pos: Int, val score: Int)

	override fun part2(input: Input): Long {
		val p1WinningWorldsMap = mutableMapOf<Int, Long>()
		val p2WinningWorldsMap = mutableMapOf<Int, Long>()
		val p1indeterminateWorldsMap = mutableMapOf<Int, Long>()
		val p2indeterminateWorldsMap = mutableMapOf<Int, Long>()
		val player1 = Player(input[0].last().digitToInt(), 0)
		val player2 = Player(input[1].last().digitToInt(), 0)
		bfs(0, 1, player1, p1WinningWorldsMap, p1indeterminateWorldsMap)
		bfs(0, 1, player2, p2WinningWorldsMap, p2indeterminateWorldsMap)
		var worldsP1 = 0L
		var worldsP2 = 0L
		for (i in p1WinningWorldsMap.keys) {
			worldsP1 += (p1WinningWorldsMap[i] ?: 1) * (p2indeterminateWorldsMap[i-1] ?: 1)
		}
		for (i in p2WinningWorldsMap.keys) {
			worldsP2 += (p2WinningWorldsMap[i] ?: 1) * (p1indeterminateWorldsMap[i] ?: 1)
		}
		return max(worldsP1, worldsP2)
	}

	// Modified BFS that calculates in how many worlds a player has won
	// and not yet won after [depth] * 3 rolls of the quantum die.
	private fun bfs(
		depth: Int,
		worlds: Long,
		player: Player,
		winningWorldsMap: MutableMap<Int, Long>,
		indeterminateWorldsMap: MutableMap<Int, Long>
	) {
		if (player.score >= 21) {
			winningWorldsMap[depth] = winningWorldsMap[depth]?.plus(worlds) ?: worlds
			return
		} else {
			indeterminateWorldsMap[depth] = indeterminateWorldsMap[depth]?.plus(worlds) ?: worlds
			for (perm in perms) {
				bfs(depth + 1, worlds * perm.value, with(player) {
					val newPos = (pos + perm.key - 1) % 10 + 1
					val newScore = score + newPos
					Player(newPos, newScore)
				}, winningWorldsMap, indeterminateWorldsMap)
			}
		}

	}
}

fun main() {

}
