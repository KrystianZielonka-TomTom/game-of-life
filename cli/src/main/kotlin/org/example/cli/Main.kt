package org.example.cli

import org.example.domain.World
import org.example.domain.serializeCells
import kotlin.random.Random
import kotlin.system.measureTimeMillis

fun main() {
    var world = World.empty().withRandom(0,0,20000,20000, Random(System.currentTimeMillis()))
    println("Sim start")
    val timeTaken = measureTimeMillis {
        world = world.step(1)
    }

    print(world.getPart(0,0,100,100).serializeCells('_'))
    println("Total time: $timeTaken ms")
}