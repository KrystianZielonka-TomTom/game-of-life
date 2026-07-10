package org.example.api.request

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import org.example.api.dto.WorldDto

data class WorldStepRequest @JsonCreator constructor(
    @param:JsonProperty("step") @field:Min(1) @field:Max(1000) val step: Int,
    @param:JsonProperty("world") @field:NotNull val world: WorldDto,
)