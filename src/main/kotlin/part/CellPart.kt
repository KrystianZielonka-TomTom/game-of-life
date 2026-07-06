package org.example.part

data class CellPart(val data: BooleanArray, val width: Int, val height: Int) {
    override fun toString(): String {
        return "CellPart(data=${serializeCells(this, 'O')})"
    }
}