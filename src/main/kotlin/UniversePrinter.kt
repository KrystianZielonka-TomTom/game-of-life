package org.example

class UniversePrinter {
     fun print(data: WorldData) {
        //Very inefficient!
        for (y in 0 until data.height) {
            val s = StringBuilder("")
            for (x in 0 until data.width) {
               s.append(if(data.getState(x,y)) "X " else "_ ")
            }
            s.append("\n")
            println(s)
        }

    }
}