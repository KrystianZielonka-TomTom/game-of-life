package org.example.world

import org.example.part.CellPart
import kotlin.collections.HashMap
import kotlin.random.Random

//Moge zrobić typ danych który reprezentuje koordynaty globalne/lokalne/chunk


/**
 * Immutable representation of all cells in game of life. World is split into chunks of size SimConstants.CHUNK_SIZE,
 * empty chunks are disregarded. WorldData doesn't hold cell data directly, it only holds chunks that are present in world.
 */

class World private constructor(private val chunks: HashMap<Pair<Int, Int>, Chunk> = HashMap()) {

    companion object {
        internal fun getChunkIndex(globalX: Int, globalY: Int): Pair<Int, Int> {
            return Pair(Math.floorDiv(globalX, Chunk.WIDTH), Math.floorDiv(globalY, Chunk.WIDTH))
        }

        internal fun getLocalCords(globalX: Int, globalY: Int): Pair<Int, Int> {
            return Pair(Math.floorMod(globalX, Chunk.WIDTH), Math.floorMod(globalY, Chunk.WIDTH))
        }

        internal fun getGlobalCoords(localX: Int, localY: Int, chunkIndexX: Int, chunkIndexY: Int): Pair<Int, Int> {
            return Pair(chunkIndexX * Chunk.WIDTH + localX, chunkIndexY * Chunk.WIDTH + localY)
        }

        fun empty(): World {
            return World()
        }

        private fun from(chunks: HashMap<Pair<Int, Int>, Chunk>): World {
            return World(HashMap(chunks))
        }

        fun step(world: World) : World {
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

            return World(resultChunks)
        }

        private fun calcNextChunk(world: World, chunkIndex: Pair<Int, Int>): Chunk? {
            val newChunk = Chunk.empty()
            val midChunk = world.chunks[chunkIndex]
            val edge = Chunk.WIDTH-1
            var hasAlive = false

            //TODO improve checking efficiency
            for (x in 0 until Chunk.WIDTH) {
                for (y in 0 until Chunk.WIDTH) {
                    val alive = midChunk?.getState(x,y) ?: false
                    val neighbours = if (x in 1 until edge && y in 1 until edge) {
                        midChunk?.getLocalNeighboursCount(x,y) ?: 0
                    } else {
                        //Cell on the edge of chunk needs to check globally
                        val (globalX, globalY) =
                            getGlobalCoords(x, y, chunkIndex.first, chunkIndex.second)
                        world.getGlobalNeighboursCount(globalX,globalY)
                    }
                    val next = nextState(alive, neighbours)
                    if (next) {
                        newChunk.setCell(x,y, true)
                        hasAlive = true
                    }
                }
            }
            return if(hasAlive) newChunk else null
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

    fun withCell(globalX : Int, globalY : Int, state: Boolean): World {
        val chunkCoords = getChunkIndex(globalX, globalY)
        val (lx, ly) = getLocalCords(globalX, globalY)
        val newWorld = from(chunks)
        newWorld.chunks.getOrPut(chunkCoords) { Chunk.empty() }.setCell(lx, ly, state)
        return this
    }

    fun withPart(globalX : Int, globalY : Int, part: CellPart): World {
        val newChunks: HashMap<Pair<Int, Int>, Chunk> = HashMap()

        for (py in 0 until part.height) {
            for (px in 0 until part.width) {
                val nGlobalX = globalX + px
                val nGlobalY = globalY + py

                val (localX, localY) = getLocalCords(nGlobalX, nGlobalY)
                newChunks.getOrPut(getChunkIndex(nGlobalX, nGlobalY)) {Chunk.empty()}
                    .setCell(localX, localY, part.data[localY * part.width + localX])
            }
        }
        return World(newChunks)
    }

    fun withRandom(globalX : Int, globalY : Int, width: Int, height: Int, random: Random): World {
        val newChunks: HashMap<Pair<Int, Int>, Chunk> = HashMap()
        for (py in 0 until height) {
            for (px in 0 until width) {
                val nGlobalX = globalX + px
                val nGlobalY = globalY + py

                val (localX, localY) = getLocalCords(nGlobalX, nGlobalY)
                newChunks.getOrPut(getChunkIndex(nGlobalX, nGlobalY)) {Chunk.empty()}
                    .setCell(localX, localY, random.nextInt(0, 2) == 1)
            }
        }
        return World(newChunks)
    }

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

    private fun getChunk(chunkX: Int, chunkY: Int): Chunk? {
        return chunks[Pair(chunkX, chunkY)]
    }


    /**
     * Returns count of alive neighbors of cell given by global coordinates X and Y.
     * For better performance use Chunk#getNeighborsCount
     */
    private fun getGlobalNeighboursCount(globalX: Int, globalY: Int): Int {
        val (localX, localY) = getLocalCords(globalX, globalY)
        if (!Chunk.isOnBorder(localX, localY)) {
            val (chunkX, chunkY) = getChunkIndex(globalX, globalY)
            getChunk(chunkX, chunkY)?.getLocalNeighboursCount(localX, localY) ?: return 0
        }

        var count = 0
        for(i in -1..1) {
            for(j in -1..1) {
                if (i == 0 && j == 0) {
                    continue
                }
                if (getState(globalX+i, globalY+j)) {
                    count++
                }
            }
        }
        return count
    }


    /**
     * Chunk is what actually holds cell data. It doesn't know anything about World it is in. Any communication between chunks,
     * should happen using WorldData.
     */
    private class Chunk private constructor(private val cells: BooleanArray) {

        companion object {
            const val WIDTH = 64
            const val LAST = WIDTH - 1
            const val FIRST = 0

            fun empty() = Chunk(BooleanArray(WIDTH*WIDTH))
            fun from(cells: BooleanArray) = Chunk(cells)
            fun isOnBorder(localX: Int, localY: Int): Boolean = localX == FIRST || localX == LAST || localY == FIRST || localY == LAST
        }

        fun setCell(localX: Int, localY: Int, state: Boolean) {
            cells[localY * WIDTH + localX] = state
        }

        fun getState(localX: Int, localY: Int): Boolean {
            return cells[localY * WIDTH + localX]
        }

        fun getLocalNeighboursCount(localX: Int, localY: Int): Int {
            if (!isOnBorder(localX, localY)) throw IllegalArgumentException("Coordinates out of range: $localX, $localY. Must be in [1, ${LAST})")

            var count = 0
            for (i in -1..1) {
                for (j in -1..1) {
                    if (i == 0 && j == 0) {
                        continue
                    }
                    if (getState(localX + i, localY + j)) {
                        count++
                    }
                }
            }
            return count
        }
    }
}