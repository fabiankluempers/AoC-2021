import java.lang.StringBuilder
import javax.swing.text.ChangedCharSetException

class Day08 : Puzzle<Int>("Day08", 26, 61229) {

	//This is not very useful for solving the actual problem but it solves part1
	override fun part1(input: Input): Int {
		val outputValues = input.map { it.split('|')[1].trim().split(' ') }
		val occurrencesOfLength = outputValues
			.flatten()
			.groupingBy { it.length }
			.eachCount()
		return listOfNotNull(
			occurrencesOfLength[2],
			occurrencesOfLength[4],
			occurrencesOfLength[3],
			occurrencesOfLength[7],
		).sum()
	}

	override fun part2(input: Input): Int = input.sumOf(::solveLine)

	//This works but it is really not pretty. I might optimize, if i find time.
	private fun solveLine(line: String) : Int {
		val intToSignal = mutableMapOf<Int, Set<Char>>()
		val (input, output) = with(line.split('|')) {
			Pair(this[0].trim().split(' '), this[1].trim().split(' '))
		}
		val inputByLength = input.groupBy { it.length }.mapValues { it.value.map(String::toSet).toMutableList() }
		intToSignal[1] = inputByLength[2]!!.first().toSet()
		intToSignal[4] = inputByLength[4]!!.first().toSet()
		intToSignal[7] = inputByLength[3]!!.first().toSet()
		intToSignal[8] = inputByLength[7]!!.first().toSet()

		fun MutableList<Set<Char>>.deduce(numToDeduce: Int, numToUse: Int) {
			this.find {
				it.containsAll(intToSignal[numToUse]!!)
			}.also {
				requireNotNull(it)
				intToSignal[numToDeduce] = it
				this.remove(it)
			}
		}

		//deduce all digits of length 6
		val inputLength6 = inputByLength[6]!!
		//deduce 9
		inputLength6.deduce(9, 4)
		//deduce 0
		inputLength6.deduce(0, 7)
		//deduce 6
		intToSignal[6] = inputLength6.first()

		//deduce all digits of length 5
		val inputLength5 = inputByLength[5]!!
		//deduce 3
		inputLength5.deduce(3, 1)
		//deduce 5
		inputLength5.find { intToSignal[6]!!.containsAll(it) }.also {
			requireNotNull(it)
			intToSignal[5] = it
			inputLength5.remove(it)
		}
		//deduce 2
		intToSignal[2] = inputLength5.first()

		val signalToString = intToSignal.map { Pair(it.value, it.key.toString()) }.toMap()

		return output.joinToString { signalToString[it.toSet()]!! }.toInt()
	}
}