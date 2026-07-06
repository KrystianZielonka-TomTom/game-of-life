package org.example.world

internal class ChunkBuilder {
    private val cells = BooleanArray(Chunk.CHUNK_SIZE * Chunk.CHUNK_SIZE)

    fun set(x : Int, y: Int, state: Boolean): ChunkBuilder {
        cells[y * Chunk.CHUNK_SIZE + x] = state
        return this
    }

    fun isEmpty(): Boolean {
        for(cell in cells) {
            if (cell) return false
        }

        return true
    }

    fun build(): Chunk = Chunk(cells)
}

internal fun buildChunk(init: ChunkBuilder.() -> Unit): Chunk {
    val builder = ChunkBuilder()
    builder.init()
    return builder.build()
}