package org.example

class Simulation(var data: WorldData) {


    fun step() {
        val newData = WorldData(data.width, data.height)

        for (x in 0 until data.width) {
            for (y in 0 until data.height) {
                val count = data.getNeighboursCount(x, y)

                if(data.getState(x,y)) {
                    // Rule 1 - underpopulation
                    if (count < 2) newData.setState(x, y, false)
                    // Rule 2 - lives
                    else if (count in 2..3) newData.setState(x, y, true)
                    // Rule 3 - overpopulation
                    else newData.setState(x, y, false)
                } else if (count == 3) {
                    // Rule 4 - reproduction
                    newData.setState(x, y, true)
                }
            }
        }

        data = newData
    }
}