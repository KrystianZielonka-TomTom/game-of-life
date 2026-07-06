import org.example.Simulation
import org.example.part.CellPart
import org.example.world.Chunk
import org.example.world.WorldBuilder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SimulationTest {

    @Test
    fun `Glider pattern should properly change to it's second state`() {
        //Given
        val initialPart = CellPart.deserializeCells("""
            OXOO
            OOXO
            XXXO
            OOOO
        """.trimIndent())

        val expectedPart = CellPart.deserializeCells("""
            OOOO
            XOXO
            OXXO
            OXOO
        """.trimIndent())
        val initialWorld = WorldBuilder().setPart(1,1, initialPart).build()

        //When
        val world = Simulation.step(initialWorld)

        //Then
        val actualPart = world.getPart(1,1, expectedPart.width, expectedPart.height)
        assertEquals(expectedPart, actualPart) //assertJ
    }

    @Test
    fun `One cell without neighbors should die from underpopulation`() {
        val initialPart = CellPart.deserializeCells("""
            OOOO
            OOXO
            OOOO
        """.trimIndent())

        val expectedPart = CellPart.deserializeCells("""
            OOOO
            OOOO
            OOOO
        """.trimIndent())
        var world = WorldBuilder().setPart(1,1, initialPart).build()

        world = Simulation.step(world)

        val actualPart = world.getPart(1,1, expectedPart.width, expectedPart.height)
        assertEquals(expectedPart, actualPart)
    }

    @Test
    fun `Pattern that doesn't change in next simulation steps, should stay the same`() {
        val initialPart = CellPart.deserializeCells("""
            OOOO
            OXXO
            OXXO
            OOOO
        """.trimIndent())

        val expectedPart = CellPart.deserializeCells("""
            OOOO
            OXXO
            OXXO
            OOOO
        """.trimIndent())
        var world = WorldBuilder().setPart(1,1, initialPart).build()

        world = Simulation.step(world)

        val actualPart = world.getPart(1,1, expectedPart.width, expectedPart.height)
        assertEquals(expectedPart, actualPart)
    }

    @Test
    fun `Glider placed at the edge of the chunk, should create new chunks`() {
        val gliderPart = CellPart.deserializeCells("""
            OXO
            OOX
            XXX
            OOO
        """.trimIndent())

        //Places exactly in chunk (0,0) on the edge
        val initialX = Chunk.CHUNK_SIZE - gliderPart.width
        val initialY = Chunk.CHUNK_SIZE - gliderPart.height

        var world = WorldBuilder().setPart(initialX, initialY, gliderPart).build()
        val stepsToMoveByTwo = 8
        val expectedChunks = 3

        //When
        repeat (stepsToMoveByTwo) {
            world = Simulation.step(world)
        }

        val actualPart = world.getPart(initialX+2,initialY+2, gliderPart.width, gliderPart.height)

        //Then
        assertEquals(gliderPart, actualPart)
        assertEquals(expectedChunks, world.chunks.size)
    }
}