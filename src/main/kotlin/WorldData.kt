package org.example

import javax.swing.Spring.height

//With infinite map. constructor should accept initial state
class WorldData {
    val chunks: HashMap<Pair<Int, Int>, Chunk> = HashMap()

    private fun getChunkIndex(x: Int, y: Int): Pair<Int, Int> {
        return Pair(Math.floorDiv(x, SimConstants.CHUNK_SIZE), Math.floorDiv(y, SimConstants.CHUNK_SIZE))
    }

    private fun getLocalCords(x: Int, y: Int): Pair<Int, Int> {
        return Pair(x % SimConstants.CHUNK_SIZE, y % SimConstants.CHUNK_SIZE)
    }

    fun setState(x: Int, y: Int, state: Boolean) {
        val (xCh, yCh) = getChunkIndex(x,y)
        val chunk = chunks.getOrPut(Pair(xCh, yCh)) {
            return@getOrPut Chunk()
        }

        val (xLocal, yLocal) = getLocalCords(x,y)
        chunk.setState(xLocal, yLocal, state)
    }

    fun getState(x: Int, y: Int): Boolean {
        val chPair = getChunkIndex(x,y)
        if(!chunks.containsKey(chPair))
            return false
        val (xLocal, yLocal) = getLocalCords(x,y)
        return chunks[chPair]!!.getState(xLocal,yLocal)
    }

    fun getPart(x: Int, y: Int, width: Int, height: Int): CellPart {
        val data = BooleanArray(width * height)
        var c = 0
        for (yi in y until y + height) {
            for (xi in x until x + width) {
                data[c] = getState(xi,yi)
                c++
            }
        }
        return CellPart(data, width, height)
    }

    fun insertPart(x: Int, y: Int, part: CellPart) {
        var c = 0
        for (yi in y until y + part.height) {
            for (xi in x until x + part.width) {
                setState(xi,yi, part.data[c])
                c++
            }
        }
    }

    fun getNeighboursCount(x: Int, y: Int): Int {
        var count = 0
        for(i in -1..1) {
            for(j in -1..1) {
                if (i == 0 && j == 0) {
                    continue
                }
                val nx = x+i
                val ny = y+j
                if (getState(nx, ny)) { //VERY SLOW!!!
                    count++
                }
            }
        }
        return count
    }

    fun clear() {
        chunks.clear()
    }
}