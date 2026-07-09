package org.example.api.request

class WorldRandomRequest(
    val steps: Int,
    val seed: Long? = null,
    val initialX: Int,
    val initialY: Int,
    val initialWidth: Int,
    val initialHeight: Int,
    val requestedX: Int,
    val requestedY: Int,
    val requestedWidth: Int,
    val requestedHeight: Int
)