import org.example.CellPart
import org.example.deserializeCells
import org.example.serializeCells
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class CellSerializationTest {

    @Test
    fun deserializeCells_whenValidString_returnValidPart() {
        val pattern = """
            OXOO
            XOOO
            OXXO
        """.trimIndent()
        val validPattern = booleanArrayOf(
            false, true, false, false,
            true, false, false, false,
            false, true, true, false)

        val part = deserializeCells(pattern)

        Assertions.assertArrayEquals(validPattern, part.data)
    }

    @Test
    fun serializeCells_whenValidPart_returnValidString() {
        val partPattern = booleanArrayOf(
            false, true, false, false,
            true, false, false, false,
            false, true, true, false)
        val part = CellPart(partPattern, 4, 3)
        val expectedPattern = """
            OXOO
            XOOO
            OXXO
        """.trimIndent()

        val pattern = serializeCells(part, 'O')

        Assertions.assertEquals(expectedPattern, pattern)
    }
}