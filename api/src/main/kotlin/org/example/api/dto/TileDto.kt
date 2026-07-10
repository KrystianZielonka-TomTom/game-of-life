package org.example.api.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotNull

data class TileDto @JsonCreator constructor(
    @param:JsonProperty("tileIndexX") val tileIndexX: Int,
    @param:JsonProperty("tileIndexY") val tileIndexY: Int,
    @param:JsonProperty("data") @field:NotNull val data: BooleanArray
)