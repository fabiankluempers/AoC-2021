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


fun <T> T.check(message : String = "", predicate: Predicate<T>) : T =
	if (!predicate.test(this)) throw IllegalStateException(message) else this
