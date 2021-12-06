class Day06 : Puzzle<Long>("Day06", 5934, 26984457539) {
	override fun part1(input: Input): Long = improvedSolution(input, 80)

	private fun firstSolution(input: Input, numOfDays: Int) : Long {
		val lanternFishSchool = input[0].split(',').map { LanternFish(it.toInt()) }.toMutableList()
		repeat(numOfDays) { lanternFishSchool += lanternFishSchool.mapNotNull(LanternFish::createDescendant) }
		return lanternFishSchool.size.toLong()
	}

	class LanternFish(firstDescendantTimer : Int = 8) {
		private var descendentTimer: Int = firstDescendantTimer

		fun createDescendant() : LanternFish? {
			if (descendentTimer > 0) {
				descendentTimer--
				return null
			}
			descendentTimer = 6
			return LanternFish()
		}
	}

	private fun improvedSolution(input: Input, numOfDays : Int) : Long {
		val initialTimerValues = input[0].split(',').map(String::toInt)
		return initialTimerValues.groupingBy { it }.eachCount().map {
			numOfDescendantFish(it.key + 1, numOfDays) * it.value
		}.plus(initialTimerValues.count().toLong()).sum()
	}

	override fun part2(input: Input): Long = improvedSolution(input, 256)

	/**
	 * Calculates the total number of descendants that a fish produces in [numOfDays] days,
	 * when it gets its first child on the [firstDescendantOnDay] day.
	 */
	private fun numOfDescendantFish(firstDescendantOnDay: Int, numOfDays : Int) : Long {
		val dayDelta = (numOfDays - firstDescendantOnDay)
		if (dayDelta < 0) return 0
		val numBabyFish = (dayDelta / 7) + 1
		var result : Long = numBabyFish.toLong()
		for (i in 0 until numBabyFish) {
			result += numOfDescendantFish(9, dayDelta - (i * 7))
		}
		return result
	}
}
