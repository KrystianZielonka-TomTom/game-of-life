package org.example

class Simulation(var data: WorldData) {


    fun step() {
        val newData = WorldData(data.width, data.height)

        data.getAliveSet().forEach { cell ->
            run {
                val x = cell.first
                val y = cell.second
                val count = data.getAliveNeighboursCount(x, y)
                // Rule 1 - underpopulation
                if (count < 2) newData.setState(x, y, false)
                // Rule 2 - lives
                else if (count in 2..3) newData.setState(x, y, true)
                // Rule 3 - overpopulation
                else newData.setState(x, y, false)
            }
        }

        data.getDeadNeighboursSet().forEach { cell -> run {
            val x = cell.first
            val y = cell.second
            val count = data.getAliveNeighboursCount(x, y)
            // Rule 4 - reproduction
            if (count == 3) newData.setState(x, y, false)
        }}

        data = newData
    }
}