package org.example.domain

class Tile(
    val tileIndexX: Int,
    val tileIndexY: Int,
    val cells: BooleanArray
) {
    companion object {
        const val WIDTH = 64
    }
}