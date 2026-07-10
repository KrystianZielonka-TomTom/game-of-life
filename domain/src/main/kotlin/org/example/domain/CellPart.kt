package org.example.domain

import java.io.BufferedReader
import java.io.StringReader

data class CellPart(val data: BooleanArray, val dimensions: Vector2D) {
    override fun toString(): String {
        return "CellPart(data=${this.serializeCells('O')})"
    }

    companion object {
        /**
         * Deserialize cell pattern given by X-alive (everything else is dead) to CellPart.
         */
        fun deserializeCells(part: String): CellPart {

            var width = 0
            var height = 1
            val data = ArrayList<Boolean>()
            BufferedReader(StringReader(part)).use { br ->
                var c = 0
                var cols = 0

                while (br.read().also { c = it } != -1) {
                    if (c == '\n'.code) {
                        if (width == 0) {
                            width = cols
                        }
                        height++
                        continue
                    }
                    data.add(c == 'X'.code)
                    cols++
                }
            }

            return CellPart(data.toBooleanArray(), Vector2D(width, height))
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CellPart

        if (!data.contentEquals(other.data)) return false
        if (dimensions != other.dimensions) return false

        return true
    }

    override fun hashCode(): Int {
        var result = data.contentHashCode()
        result = 31 * result + dimensions.hashCode()
        return result
    }
}

/**
 * Serialize CellPart into string pattern
 */
fun CellPart.serializeCells(deadCell: Char): String {
    val sb = StringBuilder()
    var count = 0
    val max = this.dimensions.x * this.dimensions.y
    for (c in this.data) {
        count++
        if (c) {
            sb.append("X")
        } else {
            sb.append(deadCell)
        }
        if (count%this.dimensions.x == 0 && count < max) {
            sb.append('\n')
        }
    }

    return sb.toString()
}