class Day16 : Puzzle<Int>("Day16", 0, 0) {
	override fun part1(input: Input): Int {
		val (_, packet) = input.first().hexToBinary().parseBinaryToPacket()
		println(packet)
		return packet.sumOfVersions()
	}

	private fun Packet.sumOfVersions(): Int =
		when (this) {
			is LiteralPacket -> version
			is OperationPacket -> version + subPackets.sumOf { it.sumOfVersions() }
		}

	private fun String.parseBinaryToPacket(): Pair<String, Packet> {
		var index = 6
		val version = this.take(3).toInt(2)
		val packetType = this.drop(3).take(3).toInt(2)
		if (packetType == 4) {
			var last = false
			val literal = this
				.drop(index)
				.chunkedSequence(5)
				.takeWhile { chunk -> !last.also { last = chunk.first() == '0' } }
				.toList()
				.map { it.drop(1) }
				.also { index += (it.count() * 5) }
				.joinToString("") { it }
				.toLong(2)
			return Pair(this.drop(index), LiteralPacket(version, literal))
		} else {
			val lengthType = if (this[index++] == '0') LengthType.TOTAL_LENGTH else LengthType.NUM_SUB_PACKETS
			val subPackets = mutableListOf<Packet>()
			when (lengthType) {
				LengthType.TOTAL_LENGTH -> {
					val totalLength = this.substring(index, index + 15).toInt(2)
					index += 15
					var subPacketInput = this.substring(index, index + totalLength)
					index += totalLength
					while (subPacketInput.isNotEmpty()) {
						val (string, packet) = subPacketInput.parseBinaryToPacket()
						subPackets.add(packet)
						subPacketInput = string
					}
					return Pair(this.drop(index), OperationPacket(version, packetType, subPackets))
				}
				LengthType.NUM_SUB_PACKETS -> {
					val numSubPackets = this.substring(index, index + 11).toInt(2)
					index += 11
					var remainingInput = this.drop(index)
					repeat(numSubPackets) {
						val (string, packet) = remainingInput.parseBinaryToPacket()
						subPackets.add(packet)
						remainingInput = string
					}
					return Pair(remainingInput, OperationPacket(version, packetType, subPackets))
				}
			}
		}
	}

	private fun String.hexToBinary() = this
		.map { it.digitToInt(16).toString(2).addLeadingZeroes() }
		.joinToString("") { it }

	private fun String.addLeadingZeroes() = "${"0".repeat(4 - length)}$this"

	override fun part2(input: Input): Int {
		return 0
	}

	sealed class Packet()

	data class LiteralPacket(val version: Int, val literal: Long) : Packet()

	data class OperationPacket(val version: Int, val operationID: Int, val subPackets: List<Packet>) : Packet()

	enum class LengthType { TOTAL_LENGTH, NUM_SUB_PACKETS }
}

