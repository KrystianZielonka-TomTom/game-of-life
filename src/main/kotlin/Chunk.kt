package org.example

class Chunk {
    private val stateData: BooleanArray = BooleanArray(SimConstants.CHUNK_SIZE * SimConstants.CHUNK_SIZE)
    private fun getIndex(x: Int, y: Int): Int {
        return y * SimConstants.CHUNK_SIZE + x
    }

    fun setState(x: Int, y: Int, state: Boolean) {
        stateData[getIndex(x, y)] = state
    }

    fun getState(x: Int, y: Int): Boolean {
        return stateData[getIndex(x, y)]
    }

//    fun getNeighboursCount(x: Int, y: Int): Int {
//        var count = 0
//        for(i in -1..1) {
//            for(j in -1..1) {
//                if (i == 0 && j == 0) {
//                    continue
//                }
//                val nx = x+i
//                val ny = y+j
//                if (nx in 0 until width && ny in 0 until height && stateData[getIndex(nx, ny)]) {
//                    count++
//                }
//            }
//        }
//        return count
//    }
}