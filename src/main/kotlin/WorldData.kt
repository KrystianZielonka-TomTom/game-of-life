package org.example

//With infinite map. constructor should accept initial state
class WorldData(val width: Int, val height: Int) {
    private val gameMap: BooleanArray = BooleanArray(width * height)
    private val neighbourCount: IntArray = IntArray(width * height)

    private fun getIndex(x: Int, y: Int): Int {
        return y * width + x
    }

    fun setState(x: Int, y: Int, state: Boolean) {
        val ind = getIndex(x, y)

        gameMap[ind] = state

        for(i in -1..1) {
            for(j in -1..1) {
                if (i == 0 && j == 0) {
                    continue
                }
                val nx = x+i
                val ny = y+j
                if (x in 0 until width && y in 0 until height) {
                    neighbourCount[getIndex(nx,ny)]++
                }
            }
        }
        //This is not that great, we have a lot of operations for just one thing that changes
    }

    fun getState(x: Int, y: Int): Boolean {
        return gameMap[getIndex(x, y)]
    }

    fun getNeighboursCount(x: Int, y: Int): Int {
        return neighbourCount[getIndex(x, y)]
    }
}