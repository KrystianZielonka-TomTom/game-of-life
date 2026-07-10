package org.example.domain

import org.junit.jupiter.api.Test
import kotlin.random.Random
import kotlin.system.measureTimeMillis

class PerformanceTest {

    @Test
    fun `measure execution time of synchronous simulation for 1000 steps on 1000x1000 world`() {
        var world = World.empty().withRandom(0,0, 1000,1000, Random(System.currentTimeMillis()))
        val timeTaken = measureTimeMillis {
            world = world.step(1000, true)
        }
        println("Simulation time taken: $timeTaken ms") //TODO
    }

    @Test
    fun `measure execution time of asynchronous simulation for 1000 steps on 1000x1000 world`() {
        var world = World.empty().withRandom(0,0,1000,1000, Random(System.currentTimeMillis()))
        val timeTaken = measureTimeMillis {
            world = world.step(1000)
        }
        println("Simulation time taken: $timeTaken ms") //TODO
    }
}