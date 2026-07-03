package org.example

//With infinite map. constructor should accept initial state
class WorldData(val width: Int, val height: Int) {
    private val gameMap: BooleanArray = BooleanArray(width * height)

    private fun getIndex(x: Int, y: Int): Int {
        return y * width + x
    }

    fun setState(x: Int, y: Int, state: Boolean) {
        gameMap[getIndex(x, y)] = state
    }

    fun getState(x: Int, y: Int): Boolean {
        return gameMap[getIndex(x, y)]
    }

    fun getNeighboursCount(x: Int, y: Int): Int {
        var count = 0
        for(i in -1..1) {
            for(j in -1..1) {
                if (i == 0 && j == 0) {
                    continue
                }
                val nx = x+i
                val ny = y+j
                if (nx in 0 until width && ny in 0 until height && gameMap[getIndex(nx, ny)]) {
                    count++
                }
            }
        }
        return count
    }

    fun clear() {
        gameMap.fill(false)
    }
}