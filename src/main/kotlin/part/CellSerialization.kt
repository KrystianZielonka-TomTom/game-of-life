package org.example.part

import java.io.BufferedReader
import java.io.StringReader

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

    return CellPart(data.toBooleanArray(), width, height)
}

/**
 * Serialize CellPart into string pattern
 */
fun serializeCells(part: CellPart, deadCell: Char): String {
    val sb = StringBuilder()
    var count = 0
    val max = part.width * part.height
    for (c in part.data) {
        count++
        if (c) {
            sb.append("X")
        } else {
            sb.append(deadCell)
        }
        if (count%part.width == 0 && count < max) {
            sb.append('\n')
        }
    }

    return sb.toString()
}