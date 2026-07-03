import org.example.CellPart
import org.example.deserializeCells
import org.example.serializeCells
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class CellSerializationTest {

    @Test
    fun whenValidString_returnValidPart() {
        val s = """
            OXOO
            XOOO
            OXXO
        """.trimIndent()
        val part = deserializeCells(s)
        val valid = booleanArrayOf(
            false, true, false, false,
            true, false, false, false,
            false, true, true, false)

        Assertions.assertArrayEquals(valid, part.data)
    }

    @Test
    fun whenValidPart_returnValidString() {
        val a = booleanArrayOf(
            false, true, false, false,
            true, false, false, false,
            false, true, true, false)
        val part = CellPart(a, 4, 3)
        val s = serializeCells(part, 'O')

        Assertions.assertEquals("""
            OXOO
            XOOO
            OXXO
        """.trimIndent(), s)
    }
}