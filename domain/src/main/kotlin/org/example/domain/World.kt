package org.example.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

class World private constructor(private val chunks: HashMap<ChunkIndexVector2D, Chunk> = HashMap()) {

    companion object {
        internal fun getChunkIndex(global: GlobalVector2D): ChunkIndexVector2D {
            return ChunkIndexVector2D(Math.floorDiv(global.x, Chunk.WIDTH), Math.floorDiv(global.y, Chunk.WIDTH))
        }

        internal fun getLocalCords(global: GlobalVector2D): ChunkLocalVector2D {
            return ChunkLocalVector2D(Math.floorMod(global.x, Chunk.WIDTH), Math.floorMod(global.y, Chunk.WIDTH))
        }

        internal fun getGlobalCoords(local: ChunkLocalVector2D, chunk: ChunkIndexVector2D): GlobalVector2D {
            return GlobalVector2D(chunk.x * Chunk.WIDTH + local.x, chunk.y * Chunk.WIDTH + local.y)
        }

        fun empty(): World {
            return World()
        }

        private fun from(chunks: HashMap<ChunkIndexVector2D, Chunk>): World {
            return World(HashMap(chunks))
        }

        fun fromRandom(global: GlobalVector2D, dimensions: Vector2D, random: Random): World {
            //allocation that doesn't achieve anything at World()
            return World().withRandom(global, dimensions, random)
        }

        fun fromTiles(tiles: List<Tile>): World {
            val newChunks = HashMap<ChunkIndexVector2D, Chunk>()
            for (tile in tiles) {
                newChunks[tile.tileIndex] = Chunk.from(tile.cells)
            }
            return World(newChunks)
        }

        private fun calcNextChunk(world: World, chunkIndex: ChunkIndexVector2D): Chunk? {
            val newChunk = Chunk.empty()
            val midChunk = world.chunks[chunkIndex]
            var hasAlive = false

            //TODO improve checking efficiency
            for (x in 0 until Chunk.WIDTH) {
                for (y in 0 until Chunk.WIDTH) {
                    val local = ChunkLocalVector2D(x, y)

                    val alive = midChunk?.getState(local) ?: false
                    val global = getGlobalCoords(local, chunkIndex)
                    val neighbours = world.getGlobalNeighboursCount(global)
                    val next = nextState(alive, neighbours)
                    if (next) {
                        newChunk.setCell(local, true)
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

    fun step(iterations: Int = 1, synchronous: Boolean = false) : World {
        var newWorld = this
        repeat(iterations) {
            val chunksToProcess = HashSet<ChunkIndexVector2D>()

            //All neighbors of active chunk need to be evaluated as well
            for (key in newWorld.chunks.keys) {
                //Add all neighbors of middle chunk as well as middle chunk
                for(cx in -1..1) {
                    for(cy in -1..1) {
                        chunksToProcess.add(ChunkIndexVector2D(key.x + cx, key.y + cy))
                    }
                }
            }

            if (!synchronous) {
            val resultChunks = runBlocking { coroutineScope {
                            chunksToProcess.map { coords ->
                                async(Dispatchers.IO) {
                                    coords to calcNextChunk(newWorld, coords)
                                }
                            }.awaitAll()
                                .filter { it.second != null }
                                .associate { it.first to it.second!! }
                        }
                            }


                        newWorld = World(HashMap(resultChunks))
            } else {
            val resultChunks = HashMap<Pair<Int, Int>, Chunk>()

            for (coords in chunksToProcess) {
                val c = calcNextChunk(newWorld, coords)
                if (c != null) {
                    resultChunks[coords] = c
                }

            newWorld = World(resultChunks)
            }
        }
        return newWorld
    }

    fun withCell(global: GlobalVector2D, state: Boolean): World {
        val chunkCoords = getChunkIndex(global)
        val local = getLocalCords(global)
        val newWorld = from(chunks)
        newWorld.chunks.getOrPut(chunkCoords) { Chunk.empty() }.setCell(local, state)
        return this
    }

    fun withPart(global: GlobalVector2D, part: CellPart): World {
        val newChunks: HashMap<ChunkIndexVector2D, Chunk> = HashMap()

        var i = 0
        for (py in 0 until part.dimensions.y) {
            for (px in 0 until part.dimensions.x) {
                val nGlobal = GlobalVector2D(global.x + px, global.y + py)

                val local = getLocalCords(nGlobal)
                newChunks.getOrPut(getChunkIndex(nGlobal)) {Chunk.empty()}
                    .setCell(local, part.data[i])
                i++
            }
        }
        return World(newChunks)
    }

    fun withRandom(global: GlobalVector2D, dimensions: Vector2D, random: Random): World {
        val newChunks: HashMap<ChunkIndexVector2D, Chunk> = HashMap()
        for (py in 0 until dimensions.x) {
            for (px in 0 until dimensions.y) {
                val nGlobal = GlobalVector2D(global.x + px, global.y + py)

                val local = getLocalCords(nGlobal)
                newChunks.getOrPut(getChunkIndex(nGlobal)) {Chunk.empty()}
                    .setCell(local, random.nextInt(0, 2) == 1)
            }
        }
        return World(newChunks)
    }

    fun getState(global: GlobalVector2D): Boolean {
        val chPair = getChunkIndex(global)
        val chunk = chunks[chPair] ?: return false
        val local = getLocalCords(global)
        return chunk.getState(local)
    }

    fun getPart(global: GlobalVector2D, dimensions: Vector2D): CellPart {
        val data = BooleanArray(dimensions.x * dimensions.y) { false }
        var c = 0
        for (yi in global.y until global.y + dimensions.y) {
            for (xi in global.x until global.x + dimensions.x) {
                val s = getState(GlobalVector2D(xi, yi))
                data[c] = s
                c++
            }
        }
        return CellPart(data, dimensions)
    }

    fun getTiles(): List<Tile> {
        return chunks.map { (chunkCoords, chunk) ->
            Tile(chunkCoords, chunk.getCells())
        }
    }

    private fun getChunk(chunkIndex: ChunkIndexVector2D): Chunk? {
        return chunks[chunkIndex]
    }


    private fun getGlobalNeighboursCount(global: GlobalVector2D): Int {
        val local = getLocalCords(global)
        if (!Chunk.isOnBorder(local)) {
            val chunkInd = getChunkIndex(global)
            return getChunk(chunkInd)?.getLocalNeighboursCount(local) ?: 0
        }

        var count = 0
        for(i in -1..1) {
            for(j in -1..1) {
                if (i == 0 && j == 0) {
                    continue
                }
                if (getState(GlobalVector2D(global.x+i, global.y+j))) {
                    count++
                }
            }
        }
        return count
    }

    private class Chunk private constructor(private val cells: BooleanArray) {

        companion object {
            const val WIDTH = Tile.WIDTH
            const val LAST = WIDTH - 1
            const val FIRST = 0

            fun empty() = Chunk(BooleanArray(WIDTH*WIDTH))
            fun from(cells: BooleanArray) = Chunk(cells)
            fun isOnBorder(local: ChunkLocalVector2D): Boolean = local.x == FIRST || local.x == LAST || local.y == FIRST || local.y == LAST
        }

        fun setCell(local: ChunkLocalVector2D, state: Boolean) {
            cells[local.y * WIDTH + local.x] = state
        }

        fun getState(local: ChunkLocalVector2D): Boolean {
            return cells[local.y * WIDTH + local.x]
        }

        fun getCells(): BooleanArray {
            return cells.copyOf()
        }

        fun getLocalNeighboursCount(local: ChunkLocalVector2D): Int {
            if (isOnBorder(local)) throw IllegalArgumentException("Coordinates out of range: $local. Must be in [1, ${LAST})")

            var count = 0
            for (i in -1..1) {
                for (j in -1..1) {
                    if (i == 0 && j == 0) {
                        continue
                    }
                    if (getState(ChunkLocalVector2D( local.x+ i, local.y + j))) {
                        count++
                    }
                }
            }
            return count
        }
    }
}