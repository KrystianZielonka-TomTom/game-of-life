package org.example

class ChunkBuilder {
    private val cells = BooleanArray(SimConstants.CHUNK_SIZE * SimConstants.CHUNK_SIZE)

    fun set(x : Int, y: Int, state: Boolean): ChunkBuilder {
        cells[y * SimConstants.CHUNK_SIZE + x] = state
        return this
    }

    fun build(): Chunk = Chunk(cells)
}