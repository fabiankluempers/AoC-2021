import kotlin.system.measureTimeMillis

fun main() {
	val puzzle = Day16()
	val testInput = readInput("${puzzle.name}_test")
	var result: Any
	println(
		"Part 1 of ${puzzle.name} executed with TEST input in ${
			measureTimeMillis { result = puzzle.part1(testInput) }
		}ms with result: $result should be ${puzzle.testResultPart1}"
	)
	println(
		"Part 2 of ${puzzle.name} executed with TEST input in ${
			measureTimeMillis { result = puzzle.part2(testInput) }
		}ms with result: $result should be ${puzzle.testResultPart2}"
	)

	val input = readInput(puzzle.name)
	println(
		"Part 1 of ${puzzle.name} executed with REAL input in ${
			measureTimeMillis { result = puzzle.part1(input) }
		}ms with result: $result"
	)
	println(
		"Part 2 of ${puzzle.name} executed with REAL input in ${
			measureTimeMillis { result = puzzle.part2(input) }
		}ms with result: $result"
	)
}

typealias Input = List<String>