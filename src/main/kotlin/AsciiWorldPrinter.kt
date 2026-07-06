package org.example

import java.io.OutputStream

class AsciiWorldPrinter(val stream: OutputStream = System.out): WorldPrinter {

     override fun print(data: WorldData) {
        //Very inefficient
         for(cordsChunkPair in data.chunks) {
             println("Chunk (${cordsChunkPair.key.first}, ${cordsChunkPair.key.second})")
             val chunk = cordsChunkPair.value
             for (y in 0 until SimConstants.CHUNK_SIZE) {
                 val s = StringBuilder("")
                 for (x in 0 until SimConstants.CHUNK_SIZE) {
                     s.append(if(chunk.getState(x,y)) "X" else "_")
                 }
                 s.append("\n")
                 stream.write(s.toString().toByteArray())
             }
         }

    }
}