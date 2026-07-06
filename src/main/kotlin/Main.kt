package org.example

import kotlin.random.Random
import kotlin.system.measureTimeMillis

fun main() {
    val printer = AsciiWorldPrinter()

    val worldBuilder = WorldBuilder()
//    worldBuilder.setPart(1,1,deserializeCells("""
//            _X__
//            __X_
//            XXX_
//        """.trimIndent()))
    worldBuilder.setRandom(1,1,32,32, Random(System.currentTimeMillis()))
    var world = worldBuilder.build()

    println("Sim start")
    val timeTaken = measureTimeMillis {
        for (i in 0..1000) {
            world = Simulation.step(world)
        }
    }

    printer.print(world)
    println("Total time: $timeTaken ms")
}