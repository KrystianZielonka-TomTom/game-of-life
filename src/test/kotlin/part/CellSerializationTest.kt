package part

import org.example.part.CellPart
import org.example.part.serializeCells
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class CellSerializationTest {

    @Test
    fun `deserializeCells should return correct cell part given valid pattern`() {
        val pattern = """
            OXOO
            XOOO
            OXXO
        """.trimIndent()
        val validPattern = booleanArrayOf(
            false, true, false, false,
            true, false, false, false,
            false, true, true, false)

        val part = CellPart.deserializeCells(pattern)

        Assertions.assertArrayEquals(validPattern, part.data)
    }

    @Test
    fun `serializeCells should return correct string pattern given cell part`() {
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

        val pattern = part.serializeCells('O')

        Assertions.assertEquals(expectedPattern, pattern)
    }
}