package org.example

fun main() {
    val data = UniverseData(32, 32)
    data.setState(1, 1, true)
    data.setState(1, 2, true)
    data.setState(1, 3, true)
    data.setState(2, 1, true)
    data.setState(2, 2, true)
    data.setState(1, 0, true)
    val sim = Simulation(data)
    sim.step()
    sim
}