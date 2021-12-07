class Day06 : Puzzle<Long>("Day06", 5934, 26984457539) {
	override fun part1(input: Input): Long = improvedSolution2(input, 80)

	override fun part2(input: Input): Long = improvedSolution2(input, 256)

	//This solution is capable of solving only part1. It is very inefficient.
	private fun firstSolution(input: Input, numOfDays: Int): Long {
		val lanternFishSchool = input[0].split(',').map { LanternFish(it.toInt()) }.toMutableList()
		repeat(numOfDays) { lanternFishSchool += lanternFishSchool.mapNotNull(LanternFish::createDescendant) }
		return lanternFishSchool.size.toLong()
	}

	class LanternFish(firstDescendantTimer: Int = 8) {
		private var descendentTimer: Int = firstDescendantTimer

		fun createDescendant(): LanternFish? {
			if (descendentTimer > 0) {
				descendentTimer--
				return null
			}
			descendentTimer = 6
			return LanternFish()
		}
	}

	//This solution uses recursion. It is capable of solving part1 and part 2 but its still very inefficient.
	private fun improvedSolution(input: Input, numOfDays: Int): Long {
		val initialTimerValues = input[0].split(',').map(String::toInt)
		return initialTimerValues
			.groupingBy { it }
			.eachCount()
			.map { numOfDescendantFish(it.key + 1, numOfDays) * it.value }
			.plus(initialTimerValues.count().toLong())
			.sum()
	}

	//This solution is very efficient.
	private fun improvedSolution2(input: Input, numOfDays: Int): Long {
		val fishInState: ArrayDeque<Long> = ArrayDeque(9)
		val initialValues = input[0].split(',').map(String::toInt).groupingBy { it }.eachCount()
		for (i in 0..8) {
			fishInState.add(initialValues[i]?.toLong() ?: 0)
		}
		repeat(numOfDays) {
			with(fishInState) {
				addLast(removeFirst().also { this[6] += it })
			}
		}
		return fishInState.sum()
	}


	//Recursive helper function for "improvedSolution".
	//Calculates the total number of descendants that a fish produces in [numOfDays] days,
	//when it gets its first child on the [firstDescendantOnDay] day.
	private fun numOfDescendantFish(firstDescendantOnDay: Int, numOfDays: Int): Long {
		val dayDelta = (numOfDays - firstDescendantOnDay)
		if (dayDelta < 0) return 0
		val numBabyFish = (dayDelta / 7) + 1
		var result: Long = numBabyFish.toLong()
		for (i in 0 until numBabyFish) {
			result += numOfDescendantFish(9, dayDelta - (i * 7))
		}
		return result
	}
}

