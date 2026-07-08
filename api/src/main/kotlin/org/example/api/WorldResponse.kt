package org.example.api

data class WorldResponse(
    val x: Int,
    val y: Int,
    val width: Int,
    val height: Int,
    val data: BooleanArray
)