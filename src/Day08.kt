import java.lang.StringBuilder
import javax.swing.text.ChangedCharSetException

class Day08 : Puzzle<Int>("Day08", 26, 61229) {

	override fun part1(input: Input): Int = input
			.flatMap { it.split(" | ")[1].split(' ') }
			.count { it.length in listOf(2,3,4,7) }

	override fun part2(input: Input): Int = input.sumOf(::solveLine)

	//This works but it is really not pretty. I might optimize, if i find time.
	private fun solveLine(line: String) : Int {
		val intToSignal = mutableMapOf<Int, Set<Char>>()
		val (input, output) = with(line.split(" | ")) {
			Pair(this[0].split(' '), this[1].split(' '))
		}
		val inputByLength = input.groupBy { it.length }.mapValues { it.value.map(String::toSet).toMutableList() }
		intToSignal[1] = inputByLength[2]!!.first()
		intToSignal[4] = inputByLength[4]!!.first()
		intToSignal[7] = inputByLength[3]!!.first()
		intToSignal[8] = inputByLength[7]!!.first()

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

		return output.joinToString(separator = "") { signalToString[it.toSet()]!! }.toInt()
	}
}