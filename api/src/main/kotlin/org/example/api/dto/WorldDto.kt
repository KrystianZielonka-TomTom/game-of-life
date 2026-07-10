package org.example.api.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotNull

data class WorldDto @JsonCreator constructor(
    @param:JsonProperty("tiles") @field:NotNull val tiles: List<TileDto>
)