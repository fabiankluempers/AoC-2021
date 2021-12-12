class Day12 : Puzzle<Int>("Day12", 226, 3509) {

	override fun part1(input: Input): Int = solution(input, false)

	override fun part2(input: Input): Int = solution(input, true)

	private fun solution(input: Input, enableVisitTwice: Boolean): Int {
		val adjacentNodesMap = mutableMapOf<String, MutableList<String>>()
		for (line in input) {
			val (left, right) = line.split('-')
			adjacentNodesMap.putIfAbsent(left, mutableListOf(right))?.add(right)
			adjacentNodesMap.putIfAbsent(right, mutableListOf(left))?.add(left)
		}
		val paths = mutableSetOf<List<String>>()
		// (else "-") is super hacky but cant be bothered to refactor at this time
		adjacentNodesMap.findAllPaths(listOf("start"), paths, if (enableVisitTwice) null else "-")
		return paths.size
	}

	private fun MutableMap<String, MutableList<String>>.findAllPaths(
		currentPath: List<String>,
		foundPaths: MutableSet<List<String>>,
		visitTwice: String? = null,
	) {
		val currentNode = currentPath.last()

		if (currentNode == "end") {
			foundPaths.add(currentPath)
			return
		}

		val adjacentCandidates = this[currentNode]!!
			.filter { node ->
				node.allUppercase()
						|| node !in currentPath
						|| (node == visitTwice && currentPath.count { it == visitTwice } < 2)
			}

		for (node in adjacentCandidates) {
			val newPath = currentPath + node
			if (visitTwice == null && !node.allUppercase() && node != "start") {
				this.findAllPaths(newPath, foundPaths, node)
			}
			this.findAllPaths(newPath, foundPaths, visitTwice)
		}
	}

	private fun String.allUppercase() = this.all { it.isUpperCase() }
}