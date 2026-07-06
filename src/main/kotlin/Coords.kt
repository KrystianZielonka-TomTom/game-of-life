package org.example

fun getChunkIndex(x: Int, y: Int): Pair<Int, Int> {
    return Pair(Math.floorDiv(x, SimConstants.CHUNK_SIZE), Math.floorDiv(y, SimConstants.CHUNK_SIZE))
}

fun getLocalCords(globalX: Int, globalY: Int): Pair<Int, Int> {
    return Pair(Math.floorMod(globalX, SimConstants.CHUNK_SIZE), Math.floorMod(globalY, SimConstants.CHUNK_SIZE))
}