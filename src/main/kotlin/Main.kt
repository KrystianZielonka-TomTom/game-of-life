package org.example

fun main() {
    val printer = WorldPrinter()
    val data = WorldData(16, 16)
    data.setState(3, 3, true)
    data.setState(4, 3, true)
    data.setState(5, 3, true)
    data.setState(5, 2, true)
    data.setState(4, 1, true)
    printer.print(data)
    val sim = Simulation(data)
    sim.step()
    println("Step 1")
    printer.print(sim.data)
    printer.printNeighbour(sim.data)
}