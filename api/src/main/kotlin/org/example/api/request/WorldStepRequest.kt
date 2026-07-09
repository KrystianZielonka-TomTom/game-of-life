package org.example.api.request

import org.example.api.dto.WorldDto

class WorldStepRequest(
    val step: Int,
    val world: WorldDto,
)