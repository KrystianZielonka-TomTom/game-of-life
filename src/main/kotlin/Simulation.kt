package org.example

class Simulation(var data: WorldData) {
    private var bufferData: WorldData = WorldData(data.width, data.height)

    fun step() {
        for (x in 0 until data.width) {
            for (y in 0 until data.height) {
                val count = data.getNeighboursCount(x, y)

                if(data.getState(x,y)) {
                    // Rule 1 - underpopulation
                    if (count < 2) bufferData.setState(x, y, false)
                    // Rule 2 - lives
                    else if (count in 2..3) bufferData.setState(x, y, true)
                    // Rule 3 - overpopulation
                    else bufferData.setState(x, y, false)
                } else if (count == 3) {
                    // Rule 4 - reproduction
                    bufferData.setState(x, y, true)
                }
            }
        }
        data.clear()
        data = bufferData.also { bufferData = data }
    }
}