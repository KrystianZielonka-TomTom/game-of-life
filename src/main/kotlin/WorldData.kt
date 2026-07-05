package org.example

data class WorldData(val chunks: HashMap<Pair<Int, Int>, Chunk> = HashMap()) {
    private fun getChunkIndex(x: Int, y: Int): Pair<Int, Int> {
        return Pair(Math.floorDiv(x, SimConstants.CHUNK_SIZE), Math.floorDiv(y, SimConstants.CHUNK_SIZE))
    }

    private fun getLocalCords(x: Int, y: Int): Pair<Int, Int> {
        return Pair(Math.floorMod(x, SimConstants.CHUNK_SIZE), Math.floorMod(y, SimConstants.CHUNK_SIZE))
    }

    fun getState(x: Int, y: Int): Boolean {
        val chPair = getChunkIndex(x,y)
        val chunk = chunks[chPair] ?: return false
        val (xLocal, yLocal) = getLocalCords(x,y)
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