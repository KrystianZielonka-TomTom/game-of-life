package org.example.api.serialization

import org.example.domain.Tile
import org.springframework.boot.jackson.JacksonComponent
import tools.jackson.core.JsonParser
import tools.jackson.databind.DeserializationContext
import tools.jackson.databind.ValueDeserializer
import java.util.BitSet
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
        val bits = BitSet.valueOf(bytes)
        val bools = BooleanArray(Tile.WIDTH*Tile.WIDTH)
        var i = 0
        while (i < bools.size) {
            i = bits.nextSetBit(i)
            if (i == -1) break;
            bools[i] = true
            i++
        }

        return bools;
    }
}