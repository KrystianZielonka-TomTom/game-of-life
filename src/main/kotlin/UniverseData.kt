package org.example

//With infinite map. constructor should accept initial state
class UniverseData(val width: Int, val height: Int) {
    private val gameMap: BooleanArray = BooleanArray(width * height)
    private val aliveSet: Set<Pair<Int, Int>> = HashSet()
    private val deadNeighboursSet: Set<Pair<Int, Int>> = HashSet()

    fun setState(x: Int, y: Int, state: Boolean) {
        gameMap[x + y * width] = state
        aliveSet.plus(Pair(x,y))

        for(i in -1..1) {
            for(j in -1..1) {
                if (i == j) {
                    continue
                }
                deadNeighboursSet.plus(Pair(x+i,y+j))
            }
        }

        //This is not that great, we have a lot of operations for just one thing that changes
        aliveSet.minus(Pair(x,y))
    }

    fun getState(x: Int, y: Int): Boolean {
        return gameMap[x + y * width]
    }

    fun getAliveNeighboursCount(x: Int, y: Int): Int {
        var count = 0
        for (i in -1..1) {
            for (j in -1..1) {
                if (i == 0 && j == 0) {
                    continue
                }

                if (getState(x + i, y + j)) {
                    count++
                }
            }
        }
        return count
    }

    //This could also be iterating over alive elements thru iterator pattern
    //This way it could work both on array and set
    fun getAliveSet(): Set<Pair<Int, Int>> {
        return aliveSet //Mutable!
    }

    fun getDeadNeighboursSet(): Set<Pair<Int, Int>> {
        return deadNeighboursSet
    }
}