package org.example.api.dto

data class CellPartDto(
    val x: Int,
    val y: Int,
    val width: Int,
    val height: Int,
    val data: BooleanArray
)