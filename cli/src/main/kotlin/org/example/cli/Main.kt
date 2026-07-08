package org.example.cli

import org.example.domain.World
import org.example.domain.serializeCells
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
//        for (i in 0..1000) {
//            world = World.step(world)
//        }
        world = world.step(1000)
    }

//    printer.print(world)

    print(world.getPart(0,0,100,100).serializeCells('_'))
    println("Total time: $timeTaken ms")
}