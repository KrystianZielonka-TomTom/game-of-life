package org.example.visualization

import org.example.world.Chunk
import org.example.world.WorldData
import java.io.OutputStream
import kotlin.collections.iterator

class AsciiWorldPrinter(val stream: OutputStream = System.out): WorldPrinter {

     override fun print(data: WorldData) {
        //Very inefficient
         for(cordsChunkPair in data.chunks) {
             println("Chunk (${cordsChunkPair.key.first}, ${cordsChunkPair.key.second})")
             val chunk = cordsChunkPair.value
             for (y in 0 until Chunk.CHUNK_SIZE) {
                 val s = StringBuilder("")
                 for (x in 0 until Chunk.CHUNK_SIZE) {
                     s.append(if(chunk.getState(x,y)) "X" else "_")
                 }
                 s.append("\n")
                 stream.write(s.toString().toByteArray())
             }
         }

    }
}