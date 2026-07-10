package org.example.domain

class Tile(
    val tileIndex: ChunkIndexVector2D,
    val cells: BooleanArray
) {
    companion object {
        const val WIDTH = 64
    }
}