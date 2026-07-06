package org.example

import org.example.world.Chunk
import org.example.world.ChunkBuilder
import org.example.world.WorldData
import org.example.world.getGlobalCoords

object Simulation {

    /**
     * Performs game of life logic on WorldData and returns new, modified WorldData.
     */
    fun step(world: WorldData) : WorldData {
        val chunksToProcess = HashSet<Pair<Int, Int>>()
        //All neighbors of active chunk need to be evaluated as well
        for (key in world.chunks.keys) {
            val x = key.first
            val y = key.second
            //Add all neighbors of middle chunk as well as middle chunk
            for(cx in -1..1) {
                for(cy in -1..1) {
                    chunksToProcess.add(Pair(x + cx, y + cy))
                }
            }
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
        val edge = Chunk.CHUNK_SIZE-1
        var hasAlive = false

        //TODO improve checking efficiency
        for (x in 0 until Chunk.CHUNK_SIZE) {
            for (y in 0 until Chunk.CHUNK_SIZE) {
                val alive = midChunk?.getState(x,y) ?: false
                val neighbours = if (x in 1 until edge && y in 1 until edge) {
                    midChunk?.getNeighboursCount(x,y) ?: 0
                } else {
                    //Cell on the edge of chunk needs to check globally
                    val (globalX, globalY) =
                        getGlobalCoords(x, y, chunkIndex.first, chunkIndex.second)
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