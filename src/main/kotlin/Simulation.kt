package org.example

object Simulation {

    /**
     * Performs game of life logic on WorldData and returns new, modified WorldData.
     */
    fun step(world: WorldData) : WorldData {
        val chunksToProcess = HashSet<Pair<Int, Int>>()
        //All neighbors of active chunk need to be evaluated as well
        for (key in world.chunks.keys) {
            chunksToProcess.add(key)
            val x = key.first
            val y = key.second
            //Add all neighbors of middle chunk
            chunksToProcess.add(Pair(x-1, y-1))
            chunksToProcess.add(Pair(x-1, y))
            chunksToProcess.add(Pair(x-1, y+1))
            chunksToProcess.add(Pair(x, y-1))
            chunksToProcess.add(Pair(x, y+1))
            chunksToProcess.add(Pair(x+1, y-1))
            chunksToProcess.add(Pair(x+1, y))
            chunksToProcess.add(Pair(x+1, y+1))
        }

        val resultChunks = HashMap<Pair<Int, Int>, Chunk>()

        for (coords in chunksToProcess) {
            val c = calcNextChunk(world, coords)
            if (c != null) {
                resultChunks[coords] = c
            }
        }

        return WorldData(resultChunks)
    }

    private fun calcNextChunk(world: WorldData, chunkIndex: Pair<Int, Int>): Chunk? {
        val chunkBuilder = ChunkBuilder()
        val midChunk = world.chunks[chunkIndex]
        val edge = SimConstants.CHUNK_SIZE-1
        var hasAlive = false

        //TODO improve checking efficiency
        for (x in 0 until SimConstants.CHUNK_SIZE) {
            for (y in 0 until SimConstants.CHUNK_SIZE) {
                val alive = midChunk?.getState(x,y) ?: false
                val neighbours = if (x in 1 until edge && y in 1 until edge) {
                    midChunk?.getNeighboursCount(x,y) ?: 0
                } else {
                    //Cell on the edge of chunk needs to check globally
                    val globalX = chunkIndex.first * SimConstants.CHUNK_SIZE + x
                    val globalY = chunkIndex.second * SimConstants.CHUNK_SIZE + y
                    world.getNeighboursCount(globalX,globalY)
                }
                val next = nextState(alive, neighbours)
                if (next) {
                    chunkBuilder.set(x,y,true)
                    hasAlive = true
                }
            }
        }
        return if(hasAlive) chunkBuilder.build() else null
    }

    private fun nextState(state: Boolean, neighbourCount: Int): Boolean {
        //TODO this can probably be just a single boolean expression without if statements
        if(state) {
            // Rule 1 - underpopulation
            return if (neighbourCount < 2) false
            // Rule 2 - lives
            else if (neighbourCount in 2..3) true
            // Rule 3 - overpopulation
            else false
        } else if (neighbourCount == 3) {
            // Rule 4 - reproduction
            return true
        }
        return false
    }
}