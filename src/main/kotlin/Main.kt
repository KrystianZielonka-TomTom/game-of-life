package org.example

import org.example.world.World
import kotlin.random.Random
import kotlin.system.measureTimeMillis

fun main() {
//    val printer = AsciiWorldPrinter()

//    worldBuilder.setPart(1,1,deserializeCells("""
//            _X__
//            __X_
//            XXX_
//        """.trimIndent()))
    var world = World.empty().withRandom(0,0,20,20, Random(System.currentTimeMillis()))
    println("Sim start")
    val timeTaken = measureTimeMillis {
        for (i in 0..1000) {
            world = World.step(world)
        }
    }

//    printer.print(world)
    println("Total time: $timeTaken ms")
}