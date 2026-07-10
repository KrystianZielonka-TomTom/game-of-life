package org.example.cli

import org.example.domain.GlobalVector2D
import org.example.domain.Vector2D
import org.example.domain.World
import kotlin.random.Random
import kotlin.system.measureTimeMillis

fun main() {
    var world = World.empty().withRandom(GlobalVector2D(0,0), Vector2D(20000, 20000), Random(System.currentTimeMillis()))
    println("Sim start")
    val timeTaken = measureTimeMillis {
        world = world.step(1)
    }

    print(world.getPart(GlobalVector2D(0,0), Vector2D(100, 100)).serializeCells(' '))
    println("Total time: $timeTaken ms")
}