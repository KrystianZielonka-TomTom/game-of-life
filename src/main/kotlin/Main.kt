package org.example

import org.example.visualization.AsciiWorldPrinter
import org.example.world.buildWorld
import kotlin.random.Random
import kotlin.system.measureTimeMillis

fun main() {
    val printer = AsciiWorldPrinter()

//    worldBuilder.setPart(1,1,deserializeCells("""
//            _X__
//            __X_
//            XXX_
//        """.trimIndent()))
    var world = buildWorld {
        setRandom(1,1,32,32, Random(System.currentTimeMillis()))
    }
    println("Sim start")
    val timeTaken = measureTimeMillis {
        for (i in 0..1000) {
            world = Simulation.step(world)
        }
    }

    printer.print(world)
    println("Total time: $timeTaken ms")
}