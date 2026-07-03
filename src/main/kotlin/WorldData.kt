package org.example

//With infinite map. constructor should accept initial state
class WorldData {
    val chunks: HashMap<Pair<Int, Int>, Chunk> = HashMap()

    private fun getChunkCords(x: Int, y: Int): Pair<Int, Int> {
        return Pair(x / SimConstants.CHUNK_SIZE, y / SimConstants.CHUNK_SIZE) //TODO
    }
    private fun getCordsInChunk(x: Int, y: Int): Pair<Int, Int> {
        return Pair(x % SimConstants.CHUNK_SIZE, y % SimConstants.CHUNK_SIZE) //TODO
    }

    fun setState(x: Int, y: Int, state: Boolean) {
        val (xCh, yCh) = getChunkCords(x,y)
        val chunk = chunks.getOrPut(Pair(xCh, yCh)) {
            return@getOrPut Chunk()
        }

        val (xIn, yIn) = getCordsInChunk(x,y)
        chunk.setState(xIn, yIn, state)
    }

    fun getState(x: Int, y: Int): Boolean {
        val chPair = getChunkCords(x,y)
        if(!chunks.containsKey(chPair))
            return false
        val (xIn, yIn) = getCordsInChunk(x,y)
        return chunks[chPair]!!.getState(xIn,yIn)
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