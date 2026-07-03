package org.example

class Simulation(var data: WorldData) {
    private var bufferData: WorldData = WorldData()

    fun step() : WorldData {
        //TODO data in individual chunks should be accessed without recalculating global position each time
        for (cordsChunkPair in data.chunks) {
            for (localX in 0 until SimConstants.CHUNK_SIZE) {
                for (localY in 0 until SimConstants.CHUNK_SIZE) {
                    val globalX = localX + (cordsChunkPair.key.first * SimConstants.CHUNK_SIZE)
                    val globalY = localY + (cordsChunkPair.key.second * SimConstants.CHUNK_SIZE)

                    val count = data.getNeighboursCount(globalX, globalY)
                    if(cordsChunkPair.value.getState(localX,localY)) {
                        // Rule 1 - underpopulation
                        if (count < 2) bufferData.setState(globalX, globalY, false)
                        // Rule 2 - lives
                        else if (count in 2..3) bufferData.setState(globalX, globalY, true)
                        // Rule 3 - overpopulation
                        else bufferData.setState(globalX, globalY, false)
                    } else if (count == 3) {
                        // Rule 4 - reproduction
                        bufferData.setState(globalX, globalY, true)
                    }
                }
            }
        }

//        data.clear()
//        data = bufferData.also { bufferData = data }
        return bufferData //TODO bad fix
    }
}