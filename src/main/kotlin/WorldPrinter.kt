package org.example

class WorldPrinter {
     fun print(data: WorldData) {
        //Very inefficient!
         for(cordsChunkPair in data.chunks) {
             println("Chunk (${cordsChunkPair.key.first}, ${cordsChunkPair.key.second})")
             val chunk = cordsChunkPair.value
             for (y in 0 until SimConstants.CHUNK_SIZE) {
                 val s = StringBuilder("")
                 for (x in 0 until SimConstants.CHUNK_SIZE) {
                     s.append(if(chunk.getState(x,y)) "X " else "_ ")
                 }
                 s.append("\n")
                 println(s)
             }
         }

    }

//    fun printNeighbour(data: WorldData) {
//        for (x in 0 until data.width) {
//            val s = StringBuilder("")
//            for (y in 0 until data.height) {
//                s.append(data.getNeighboursCount(x,y))
//                s.append(" ")
//            }
//            s.append("\n")
//            println(s)
//        }
//    }
}