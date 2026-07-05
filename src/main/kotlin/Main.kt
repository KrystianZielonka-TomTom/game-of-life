package org.example

import kotlin.system.measureTimeMillis

fun main() {
    val printer = WorldPrinter()

    val worldBuilder = WorldBuilder()
    worldBuilder.setPart(1,1,deserializeCells("""
            _X__
            __X_
            XXX_
        """.trimIndent()))
    var world = worldBuilder.build()

    println("Sim start")
    val timeTaken = measureTimeMillis {
        for (i in 0..50) {
            world = Simulation.step(world)
        }
    }

    printer.print(world)
    println("Total time: $timeTaken ms")
}