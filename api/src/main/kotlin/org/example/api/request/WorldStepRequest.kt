package org.example.api.request

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import org.example.api.dto.WorldDto

class WorldStepRequest @JsonCreator constructor(
    @JsonProperty("step") val step: Int,
    @JsonProperty("world") val world: WorldDto,
)