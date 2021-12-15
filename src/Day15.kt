import java.util.*

class Day15 : Puzzle<Int>("Day15", 40, 315) {
	override fun part1(input: Input): Int {
		val graph = generateGraph(input)

		val path = dijkstra(graph, Array2dIndex(0, 0))

		return path.drop(1).sumOf(Node::weight)
	}

	// Implementation of Dijkstra-Algorithm
	private fun dijkstra(graph: Array2d<Node>, start: Array2dIndex): List<Node> {
		val pq = PriorityQueue<Node>(graph.sumOf { it.size }) { it, other ->
			it.distance - other.distance
		}

		graph[start].distance = 0
		for (row in graph.indices) {
			for (col in graph[row].indices) {
				pq.add(graph[row][col])
			}
		}
		while (pq.isNotEmpty()) {
			val u = pq.remove()
			if (u == graph.last().last()) {
				break
			}
			val adjacent = u.index.getHorAndVerAdjacentIndices().filter { it validIndexOf graph }.map { graph[it] }
			for (v in adjacent) {
				if (pq.remove(v)) {
					val alternative = u.distance + v.weight
					if (alternative < v.distance) {
						v.distance = alternative
						v.prev = u
					}
					pq.add(v)
				}
			}
		}
		return buildList<Node> {
			var node: Node? = graph.last().last()
			while (node != null) {
				add(0, node)
				node = node.prev
			}
		}
	}

	override fun part2(input: Input): Int {
		val inputAsList = input.map { it.map(Char::digitToInt) }

		fun customIncrement(int: Int): Int = if (int >= 9) 1 else int + 1

		val expandedCols = generateSequence(inputAsList) { next -> next.map { row -> row.map(::customIncrement) } }
			.take(5)
			.flatten()
			.toList()

		val expandedInput = expandedCols.map { row ->
			generateSequence(row) { next -> next.map(::customIncrement) }
				.take(5)
				.flatten()
				.toList()
		}


		val graph = Array(expandedInput.size) { row ->
			Array(expandedInput[row].size) { col ->
				Node(
					weight = expandedInput[row][col],
					index = Array2dIndex(row, col),
				)
			}
		}

		val path = dijkstra(graph, Array2dIndex(0, 0))
		return path.drop(1).sumOf { it.weight }
	}

	private fun generateGraph(input: Input): Array2d<Node> = Array(input.size) { row ->
		Array(input[row].length) { col ->
			Node(
				weight = input[row][col].digitToInt(),
				index = Array2dIndex(row, col),
			)
		}
	}

	data class Node(
		val weight: Int,
		val index: Array2dIndex,
		var distance: Int = Int.MAX_VALUE,
		var prev: Node? = null
	)
}