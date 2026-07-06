package org.example.world

import org.example.part.CellPart

/**
 * Immutable representation of all cells in game of life. World is split into chunks of size SimConstants.CHUNK_SIZE,
 * empty chunks are disregarded. WorldData doesn't hold cell data directly, it only holds chunks that are present in world.
 */
@ConsistentCopyVisibility
data class WorldData internal constructor(internal val chunks: HashMap<Pair<Int, Int>, Chunk> = HashMap()) {
    fun getState(x: Int, y: Int): Boolean {
        val chPair = getChunkIndex(x, y)
        val chunk = chunks[chPair] ?: return false
        val (xLocal, yLocal) = getLocalCords(x, y)
        return chunk.getState(xLocal,yLocal)
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


    /**
     * Returns count of alive neighbors of cell given by global coordinates X and Y.
     * For better performance use Chunk#getNeighborsCount
     */
    fun getNeighboursCount(x: Int, y: Int): Int {
        var count = 0
        for(i in -1..1) {
            for(j in -1..1) {
                if (i == 0 && j == 0) {
                    continue
                }
                if (getState(x+i, y+j)) { //VERY SLOW!!!
                    count++
                }
            }
        }
        return count
    }
}