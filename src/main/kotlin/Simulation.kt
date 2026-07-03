package org.example

class Simulation(var data: WorldData) {
    private var bufferData: WorldData = WorldData()

    fun step() {
        for (cordsChunkPair in data.chunks) {
            for (x in 0 until SimConstants.CHUNK_SIZE) {
                for (y in 0 until SimConstants.CHUNK_SIZE) {
                    val gX = x + (cordsChunkPair.key.first * SimConstants.CHUNK_SIZE)
                    val gY = y + (cordsChunkPair.key.second * SimConstants.CHUNK_SIZE)

                    val count = data.getNeighboursCount(gX, gY)
                    if(cordsChunkPair.value.getState(x,y)) {
                        // Rule 1 - underpopulation
                        if (count < 2) bufferData.setState(gX, gY, false)
                        // Rule 2 - lives
                        else if (count in 2..3) bufferData.setState(gX, gY, true)
                        // Rule 3 - overpopulation
                        else bufferData.setState(gX, gY, false)
                    } else if (count == 3) {
                        // Rule 4 - reproduction
                        bufferData.setState(gX, gY, true)
                    }
                }
            }
        }

        data.clear()
        data = bufferData.also { bufferData = data }
    }
}