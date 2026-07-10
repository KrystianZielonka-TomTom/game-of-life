package org.example.api.request

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min

data class WorldRandomRequest(
    @field:Min(1) @field:Max(1000) val steps: Int,
    val seed: Long? = null,
    val initialX: Int,
    val initialY: Int,
    @field:Min(1) val initialWidth: Int,
    @field:Min(1) val initialHeight: Int,
    val requestedX: Int,
    val requestedY: Int,
    @field:Min(1) val requestedWidth: Int,
    @field:Min(1) val requestedHeight: Int
)