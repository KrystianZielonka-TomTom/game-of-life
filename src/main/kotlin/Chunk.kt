package org.example

/**
 * Chunk is what actually holds cell data. It doesn't know anything about World it is in. Any communication between chunks,
 * should happen using WorldData.
 */
data class Chunk(private val cells: BooleanArray) {
    fun getState(x: Int, y: Int): Boolean {
        return cells[y * SimConstants.CHUNK_SIZE + x]
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Chunk

        return cells.contentEquals(other.cells)
    }

    override fun hashCode(): Int {
        return cells.contentHashCode()
    }

    /**
     * Returns count of alive neighbors in this chunk. Can't see other chunks.
     */
    fun getNeighboursCount(x: Int, y: Int): Int {
        if (!(x in 1..<SimConstants.CHUNK_SIZE-1 && y in 0..<SimConstants.CHUNK_SIZE-1)) {
            throw IllegalArgumentException("Coordinates out of range: $x, $y. Must be in [1, ${SimConstants.CHUNK_SIZE-1})")
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
}