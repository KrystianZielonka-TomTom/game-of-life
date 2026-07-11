package org.example.api.serialization

import org.example.domain.Tile
import org.springframework.boot.jackson.JacksonComponent
import tools.jackson.core.JsonGenerator
import tools.jackson.databind.SerializationContext
import tools.jackson.databind.ValueSerializer
import java.util.BitSet
import kotlin.io.encoding.Base64

@JacksonComponent
class CellDataSerializer : ValueSerializer<BooleanArray>() {

    override fun serialize(
        value: BooleanArray?,
        gen: JsonGenerator?,
        ctxt: SerializationContext?
    ) {
        if (gen == null)
            throw IllegalArgumentException("gen cannot be null")
        if (value == null) return

        val bits = BitSet(Tile.WIDTH)
        for (i in value.indices) {
            if (value[i]) {
                bits.set(i)
            }
        }

        gen.writeString(Base64.encode(bits.toByteArray()))
    }

}