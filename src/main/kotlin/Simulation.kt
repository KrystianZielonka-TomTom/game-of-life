package org.example

//TODO, just do it with functional programming

object Simulation {

    fun step(world: WorldData) : WorldData {
        val worldBuilder = WorldBuilder()

        //TODO This can be simplified if we know that alive cell is on the edge of chunk
        //     Then, only chunks that could possibly create alive cell are checked, not all of them
        val chunksToProcess = world.chunks.keys + world.chunks.keys.flatMap { chunkNeighbours(it) }


        for (coords in chunksToProcess) {
            val c = calcNextChunk(world, coords)
            if (c != null) {
                val (x,y) = coords
                worldBuilder.setChunk(x,y, c)
            }
        }

        return worldBuilder.build()
    }

    private fun chunkNeighbours(chunkIndex: Pair<Int, Int>): List<Pair<Int, Int>> {
        return listOf(
            Pair(chunkIndex.first-1, chunkIndex.second-1),
            Pair(chunkIndex.first-1, chunkIndex.second),
            Pair(chunkIndex.first-1, chunkIndex.second+1),
            Pair(chunkIndex.first, chunkIndex.second-1),
            Pair(chunkIndex.first, chunkIndex.second+1),
            Pair(chunkIndex.first+1, chunkIndex.second-1),
            Pair(chunkIndex.first+1, chunkIndex.second),
            Pair(chunkIndex.first+1, chunkIndex.second+1))
    }

    private fun calcNextChunk(world: WorldData, chunkIndex: Pair<Int, Int>): Chunk? {
        val chunkBuilder = ChunkBuilder()
        var midChunk = world.chunks[chunkIndex]

        if (midChunk != null) {
            //1. Check cells that are influenced only by cells in the same chunk
            for (x in 1 until SimConstants.CHUNK_SIZE-1) {
                for (y in 1 until SimConstants.CHUNK_SIZE-1) {
                    chunkBuilder.set(x, y, nextState(midChunk.getState(x, y), midChunk.getNeighboursCount(x, y)))
                }
            }
        }


        if (midChunk == null) {
            //TODO allocating empty chunk just to later check cells in it (all are dead)
            midChunk = ChunkBuilder().build()
        }

        //TODO
        //This is simplification, for each cell, global coords are used to find chunk and local coords
        //However most of the time it is known what chunk needs to be checked
        //Top cells need x-1,y-1 ; x,y-1 ; x+1,y-1
        //We can simply read those chunks once at the start, instead of doing so for every cell

        //2. Loop over outer edges of chunk
        val edge = SimConstants.CHUNK_SIZE-1
        //top, bottom
        for (x in 0 until SimConstants.CHUNK_SIZE) {
            chunkBuilder.set(x,0, nextState(midChunk.getState(x, 0), world.getNeighboursCount(x, 0)))
            chunkBuilder.set(x,edge, nextState(midChunk.getState(x, edge), world.getNeighboursCount(x, edge)))
        }

        //left right
        for (y in 1 until edge) {
            chunkBuilder.set(0, y, nextState(midChunk.getState(0, y), world.getNeighboursCount(0, y)))
            chunkBuilder.set(edge, y, nextState(midChunk.getState(edge, y), world.getNeighboursCount(edge, y)))
        }
        //TODO check if empty
        return chunkBuilder.build()
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