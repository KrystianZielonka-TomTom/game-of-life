package org.example

//TODO, just do it with functional programming

object Simulation {

    fun step(world: WorldData) : WorldData {
        //TODO This can be simplified if we know that alive cell is on the edge of chunk
        //     Then, only chunks that could possibly create alive cell are checked, not all of them

        val chunksToProcess = HashSet<Pair<Int, Int>>()
        for (key in world.chunks.keys) {
            chunksToProcess.add(key)
            val x = key.first
            val y = key.second
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

        for (x in 0 until SimConstants.CHUNK_SIZE) {
            for (y in 0 until SimConstants.CHUNK_SIZE) {
                val alive = midChunk?.getState(x,y) ?: false
                val neighbours = if (x in 1 until edge && y in 1 until edge) {
                    midChunk?.getNeighboursCount(x,y) ?: 0
                } else {
                    world.getNeighboursCount(chunkIndex.first * SimConstants.CHUNK_SIZE + x,chunkIndex.second * SimConstants.CHUNK_SIZE + y)
                }
                val next = nextState(alive, neighbours)
                if (next) {
                    chunkBuilder.set(x,y,true)
                    hasAlive = true
                }
            }
        }

        //TODO
        //This is simplification, for each cell, global coords are used to find chunk and local coords
        //However most of the time it is known what chunk needs to be checked
        //Top cells need x-1,y-1 ; x,y-1 ; x+1,y-1
        //We can simply read those chunks once at the start, instead of doing so for every cell
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