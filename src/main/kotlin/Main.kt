package org.example

import kotlin.system.measureTimeMillis

fun main() {
    val printer = WorldPrinter()
    val data = WorldData()
    data.setState(3, 3, true)
    data.setState(4, 3, true)
    data.setState(5, 3, true)
    data.setState(5, 2, true)
    data.setState(4, 1, true)
    printer.print(data)
    val sim = Simulation(data)

    println("Sim start")
    val timeTaken = measureTimeMillis {
        for (i in 0..50) {
            sim.step()
//        println("Step $i")

//        printer.printNeighbour(sim.data)
        }
    }

    printer.print(sim.data)
    println("Total time: $timeTaken ms")
}