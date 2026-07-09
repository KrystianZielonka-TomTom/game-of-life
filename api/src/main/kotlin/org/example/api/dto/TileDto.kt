package org.example.api.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

class TileDto @JsonCreator constructor(
    @JsonProperty("tileIndexX") val tileIndexX: Int,
    @JsonProperty("tileIndexY") val tileIndexY: Int,
    @JsonProperty("data") val data: BooleanArray
)