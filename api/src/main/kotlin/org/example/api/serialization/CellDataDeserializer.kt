package org.example.api.serialization

import org.springframework.boot.jackson.JacksonComponent
import tools.jackson.core.JsonParser
import tools.jackson.databind.DeserializationContext
import tools.jackson.databind.ValueDeserializer
import kotlin.io.encoding.Base64

@JacksonComponent
class CellDataDeserializer : ValueDeserializer<BooleanArray>() {
    override fun deserialize(
        p: JsonParser?,
        ctxt: DeserializationContext?
    ): BooleanArray? {
        if (p == null)
            throw IllegalArgumentException("p cannot be null")

        val text = p.string ?: return null
        val bytes = Base64.decode(text)

        return BooleanArray(bytes.size) { i -> bytes[i] != 0.toByte() }
    }
}