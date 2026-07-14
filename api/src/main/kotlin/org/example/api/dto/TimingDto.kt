package org.example.api.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class TimingDto<T> @JsonCreator constructor(
    @param:JsonProperty("stepMs") val stepMs: Int,
    @param:JsonProperty("loadingMs") val loadingMs: Int,
    @param:JsonProperty("response") val response: T
)