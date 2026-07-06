package org.example.world

internal fun getChunkIndex(x: Int, y: Int): Pair<Int, Int> {
    return Pair(Math.floorDiv(x, Chunk.CHUNK_SIZE), Math.floorDiv(y, Chunk.CHUNK_SIZE))
}

internal fun getLocalCords(globalX: Int, globalY: Int): Pair<Int, Int> {
    return Pair(Math.floorMod(globalX, Chunk.CHUNK_SIZE), Math.floorMod(globalY, Chunk.CHUNK_SIZE))
}

internal fun getGlobalCoords(localX: Int, localY: Int, chunkIndexX: Int, chunkIndexY: Int): Pair<Int, Int> {
    return Pair(chunkIndexX * Chunk.CHUNK_SIZE + localX, chunkIndexY * Chunk.CHUNK_SIZE + localY)
}