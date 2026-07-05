package org.example

import kotlin.system.measureTimeMillis

fun main() {
    val printer = WorldPrinter()

    val worldBuilder = WorldBuilder()
    worldBuilder.setPart(2,2,deserializeCells("""
            _X__
            __X_
            XXX_
        """.trimIndent()))
    var world = worldBuilder.build()

    println("Sim start")
    val timeTaken = measureTimeMillis {
        for (i in 0..30) {
            world = Simulation.step(world)
        }
    }

    printer.print(world)
    println("Total time: $timeTaken ms")
}