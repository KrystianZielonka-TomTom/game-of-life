package org.example.world

/**
 * Chunk is what actually holds cell data. It doesn't know anything about World it is in. Any communication between chunks,
 * should happen using WorldData.
 */
internal data class Chunk(private val cells: BooleanArray) {
    fun getState(x: Int, y: Int): Boolean {
        return cells[y * CHUNK_SIZE + x]
    }

    /**
     * Returns count of alive neighbors in this chunk. Can't see other chunks.
     */
    fun getNeighboursCount(x: Int, y: Int): Int {
        if (!(x in 1..<CHUNK_SIZE-1 && y in 0..<CHUNK_SIZE-1)) {
            throw IllegalArgumentException("Coordinates out of range: $x, $y. Must be in [1, ${CHUNK_SIZE-1})")
        }
        var count = 0
        for(i in -1..1) {
            for(j in -1..1) {
                if (i == 0 && j == 0) {
                    continue
                }
                if (getState(x+i, y+j)) {
                    count++
                }
            }
        }
        return count
    }

    companion object {
        const val CHUNK_SIZE = 64
    }
}