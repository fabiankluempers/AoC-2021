import java.io.File
import java.lang.IllegalStateException
import java.math.BigInteger
import java.security.MessageDigest
import java.util.function.Predicate

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("inputs/$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

/**
 * Splits string at [delimiter], trys to convert the resulting strings to int and returns a list containing those Ints.
 */
fun String.splitToInt(delimiter: Char) = split(delimiter).map(String::toInt)


fun <T> T.check(message: String = "", predicate: Predicate<T>): T =
	if (!predicate.test(this)) throw IllegalStateException(message) else this

/**
 * Identity function { x -> x }
 */
fun <T> identity(x: T): T = x

/**
 * Generates a Sequence consisting of [x],[x], ...
 */
fun <T> cycle(x: T): Sequence<T> = generateSequence(x, ::identity)

/**
 * Maps [transform] over each element in each iterable in this iterable.
 */
fun <T, R> Iterable<Iterable<T>>.map2d(transform: (T) -> R) = this.map { it.map(transform) }

/**
 * Represents a point in 2d space.
 */
data class Point(val x: Int, val y: Int)


//2d Array stuff
typealias Array2d<T> = Array<Array<T>>

data class Array2dIndex(val row: Int, val column: Int)

fun Array2dIndex.getAdjacentIndices() = setOf(
	Array2dIndex(row, (column + 1)),
	Array2dIndex(row, (column - 1)),
	Array2dIndex((row + 1), column),
	Array2dIndex((row - 1), column),
	Array2dIndex((row + 1), (column + 1)),
	Array2dIndex((row + 1), (column - 1)),
	Array2dIndex((row - 1), (column + 1)),
	Array2dIndex((row - 1), (column - 1)),
)

operator fun <T> Array2d<T>.get(array2dIndex: Array2dIndex) = this[array2dIndex.row][array2dIndex.column]

operator fun <T> Array2d<T>.set(array2dIndex: Array2dIndex, value: T) {
	this[array2dIndex.row][array2dIndex.column] = value
}

infix fun <T> Array2dIndex.validIndexOf(array2d: Array2d<T>) =
	(row in array2d.indices && column in array2d[row].indices)

fun <T> Array2d<T>.update(transform: (T) -> T) {
	for (row in this.indices) {
		for (column in this[row].indices) {
			this[row][column] = transform(this[row][column])
		}
	}
}

fun <T> Array2d<T>.updateAt(array2dIndex: Array2dIndex, transform: (T) -> T) {
	this[array2dIndex] = transform(this[array2dIndex])
}

fun <T> Array2d<T>.indicesWhere(predicate: Predicate<T>): Set<Array2dIndex> {
	val indices = mutableListOf<Array2dIndex>()
	for (row in this.indices) {
		for (column in this[row].indices) {
			if (predicate.test(this[row][column])) indices += Array2dIndex(row, column)
		}
	}
	return indices.toSet()
}

fun <T> Array2d<T>.allCells(predicate: Predicate<T>): Boolean = this.all { it.all { cell -> predicate.test(cell) } }

fun <T> Array2d<T>.anyCell(predicate: Predicate<T>): Boolean = this.any { it.any { cell -> predicate.test(cell) } }