package org.example.api

import org.springframework.boot.jackson.JacksonComponent
import tools.jackson.databind.SerializationContext
import tools.jackson.databind.ValueSerializer
import kotlin.io.encoding.Base64

@JacksonComponent
class CellDataSerializer : ValueSerializer<BooleanArray>() {

    override fun serialize(
        value: BooleanArray?,
        gen: tools.jackson.core.JsonGenerator?,
        ctxt: SerializationContext?
    ) {
        if (gen == null)
            throw IllegalArgumentException("gen cannot be null")
        if (value == null) return

        gen.writeString(Base64.encode(ByteArray(value.size) { i -> if (value[i]) 1 else 0 }))
    }

}