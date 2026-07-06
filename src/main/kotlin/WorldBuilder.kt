package org.example

import kotlin.collections.HashMap

class WorldBuilder {
    val chunks: HashMap<Pair<Int, Int>, Chunk> = HashMap()
    val chunkBuilders: HashMap<Pair<Int, Int>, ChunkBuilder> = HashMap()

    fun setCell(x : Int, y : Int, state: Boolean): WorldBuilder {
        val chunkCoords = getChunkIndex(x, y)
        val (lx, ly) = getLocalCords(x, y)
        chunkBuilders.getOrPut(chunkCoords) { ChunkBuilder() }.set(lx, ly, state)
        return this
    }

    fun setChunk(x : Int, y : Int, chunk: Chunk) : WorldBuilder {
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

    fun build(): WorldData {
        val ch = HashMap<Pair<Int, Int>, Chunk>()
        chunkBuilders.forEach { (pair, builder) -> ch[pair] = builder.build() }
        chunks.forEach { (pair, builder) -> ch[pair] = builder }
        return WorldData(ch)
    }
}