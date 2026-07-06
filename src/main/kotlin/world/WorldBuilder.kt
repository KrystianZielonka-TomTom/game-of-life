package org.example.world

import org.example.part.CellPart
import kotlin.collections.HashMap
import kotlin.random.Random

class WorldBuilder {
    private val chunks: HashMap<Pair<Int, Int>, Chunk> = HashMap()
    private val chunkBuilders: HashMap<Pair<Int, Int>, ChunkBuilder> = HashMap()

    fun setCell(x : Int, y : Int, state: Boolean): WorldBuilder {
        val chunkCoords = getChunkIndex(x, y)
        val (lx, ly) = getLocalCords(x, y)
        chunkBuilders.getOrPut(chunkCoords) { ChunkBuilder() }.set(lx, ly, state)
        return this
    }

    internal fun setChunk(x : Int, y : Int, chunk: Chunk) : WorldBuilder {
        this.chunks[Pair(x, y)] = chunk
        return this
    }

    fun setPart(x : Int, y : Int, part: CellPart): WorldBuilder {
        for (py in 0 until part.height) {
            for (px in 0 until part.width) {
                setCell(x + px, y + py, part.data[py * part.width + px])
            }
        }

        return this
    }

    fun setRandom(x : Int, y : Int, width: Int, height: Int, random: Random): WorldBuilder {
        for (py in 0 until height) {
            for (px in 0 until width) {
                setCell(x + px, y + py, random.nextBoolean())
            }
        }

        return this
    }

    fun build(): WorldData {
        val ch = HashMap<Pair<Int, Int>, Chunk>()
        chunkBuilders.forEach { (pair, builder) -> ch[pair] = builder.build() }
        chunks.forEach { (pair, builder) -> ch[pair] = builder }
        return WorldData(ch)
    }
}

fun buildWorld(init: WorldBuilder.() -> Unit): WorldData {
    val builder = WorldBuilder()
    builder.init()
    return builder.build()
}