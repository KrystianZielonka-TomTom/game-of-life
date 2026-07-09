package org.example.api.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

class WorldDto @JsonCreator constructor(
    @JsonProperty("tiles") val tiles: List<TileDto>
)