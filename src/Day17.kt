import kotlin.math.absoluteValue

class Day17 : Puzzle<Int>("Day17", 45, 112) {
	private val regex = "-?\\d+".toRegex()

	override fun part1(input: Input): Int {
		val bounds = regex.findAll(input.first()).map { it.value.toInt() }.toList()
		return gauss(bounds[2])
	}

	override fun part2(input: Input): Int {
		val bounds = regex.findAll(input.first()).map { it.value.toInt() }.toList()
		val xRange = bounds[0]..bounds[1]
		val yRange = bounds[2]..bounds[3]
		val xCandidates = (0..xRange.last).dropWhile { gauss(it) !in xRange }
		val yCandidates = yRange.first..yRange.first.absoluteValue
		var result = 0
		for (xCandidate in xCandidates) {
			for (yCandidate in yCandidates) {
				var x = xCandidate
				var y = yCandidate
				var xPos = 0
				var yPos = 0
				while (yPos >= yRange.first && xPos <= xRange.last) {
					if (xPos in xRange && yPos in yRange) {
						result++
						break
					}
					xPos += x.also { x = if (x == 0) 0 else x - 1 }
					yPos += y--
				}
			}
		}
		return result
	}

	private fun gauss(n: Int) = (n * n + n) / 2

}