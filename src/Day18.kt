class Day18 : Puzzle<Int>("Day18", 4140, 3993) {
	override fun part1(input: Input): Int {
		return input.map { it.parseToGraph() }.reduce { acc, graphElement -> (acc + graphElement) }.toMagnitude()
	}

	private fun String.parseToGraph(): GraphElement<Int> {
		if (length == 1) return Leaf(toInt())
		var depth = 0
		for (i in indices) {
			when (this[i]) {
				'[' -> depth++
				']' -> depth--
				',' -> if (depth == 1) return Node(
					leftChild = substring((1 until i)).parseToGraph(),
					rightChild = substring((i + 1 until lastIndex)).parseToGraph(),
				)
			}
		}
		error("cant parse $this")
	}

	private fun GraphElement<Int>.reduce(): GraphElement<Int> {
		var splitChange = true
		while (splitChange) {
			var explodeChange = true
			while (explodeChange) {
				explodeChange = this.explode()
			}
			splitChange = this.split()
		}
		return this
	}

	private fun GraphElement<Int>.toMagnitude(): Int {
		return when (this) {
			is Node -> leftChild.toMagnitude() * 3 + rightChild.toMagnitude() * 2
			is Leaf -> label
		}
	}

	private fun GraphElement<Int>.explode(depth: Int = 0): Boolean {
		var changed = false
		if (this is Node) {
			if (depth < 4) {
				changed = leftChild.explode(depth + 1)
				if (!changed) {
					changed = rightChild.explode(depth + 1)
				}
			} else {
				parent?.addValueLeftUp((leftChild as Leaf).label, this)
				parent?.addValueRightUp((rightChild as Leaf).label, this)
				if (parent?.leftChild === this) {
					parent?.leftChild = Leaf(0)
				}
				if (parent?.rightChild === this) {
					parent?.rightChild = Leaf(0)
				}
				changed = true
			}
		}
		return changed
	}

	private fun Node<Int>.addValueRightUp(value: Int, prev: GraphElement<Int>) {
		if (prev === rightChild) {
			parent?.addValueRightUp(value, this)
		} else {
			rightChild.addValueRightDown(value)
		}
	}

	private fun GraphElement<Int>.addValueRightDown(value: Int) {
		when (this) {
			is Leaf -> this.label += value
			is Node -> {
				leftChild.addValueRightDown(value)
			}
		}
	}

	private fun Node<Int>.addValueLeftUp(value: Int, prev: GraphElement<Int>) {
		if (prev === leftChild) {
			parent?.addValueLeftUp(value, this)
		} else {
			leftChild.addValueLeftDown(value)
		}
	}

	private fun GraphElement<Int>.addValueLeftDown(value: Int) {
		when (this) {
			is Leaf -> this.label += value
			is Node -> {
				rightChild.addValueLeftDown(value)
			}
		}
	}

	private fun GraphElement<Int>.split(): Boolean {
		var changed = false
		if (this is Node) {
			val lc = leftChild
			val rc = rightChild
			if (lc is Leaf && lc.label >= 10) {
				this.leftChild = Node(leftChild = Leaf(lc.label / 2), rightChild = Leaf((lc.label + 1) / 2))
				changed = true
			}
			if (!changed && lc is Node) {
				changed = lc.split()
			}
			if (!changed && rc is Leaf && rc.label >= 10) {
				this.rightChild = Node(leftChild = Leaf(rc.label / 2), rightChild = Leaf((rc.label + 1) / 2))
				changed = true
			}
			if (!changed && rc is Node) {
				changed = rc.split()
			}
		} else {
			error("cant split a leaf")
		}
		return changed
	}

	private operator fun GraphElement<Int>.plus(other: GraphElement<Int>): GraphElement<Int> {
		return Node(leftChild = this, rightChild = other).reduce()
	}

	override fun part2(input: Input): Int {
		return input.flatMap { line ->
			input.flatMap { other -> if (line != other) { listOf(
				//need to parse them over and over again, because adding modifies the graph
					(line.parseToGraph() + other.parseToGraph()).toMagnitude(),
					(other.parseToGraph() + line.parseToGraph()).toMagnitude())
			} else listOf() }
		}.maxOf(::identity)
	}

	sealed class GraphElement<T>(var parent: Node<T>?)

	class Node<T>(leftChild: GraphElement<T>, rightChild: GraphElement<T>) : GraphElement<T>(null) {
		var leftChild: GraphElement<T> = leftChild
			set(value) {
				value.parent = this
				field = value
			}
		var rightChild: GraphElement<T> = rightChild
			set(value) {
				value.parent = this
				field = value
			}

		init {
			leftChild.parent = this
			rightChild.parent = this
		}

		override fun toString(): String = "[$leftChild,$rightChild]"
	}

	data class Leaf<T>(var label: T) : GraphElement<T>(null) {
		override fun toString(): String = "$label"
	}
}



