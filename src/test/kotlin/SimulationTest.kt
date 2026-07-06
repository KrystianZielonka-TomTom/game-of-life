import org.example.Simulation
import org.example.part.CellPart
import org.example.world.WorldBuilder
import org.example.world.Chunk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class SimulationTest {

    @Test
    fun `Glider pattern should properly change to it's second state`() {
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
        var world = WorldBuilder().setPart(1,1, initialPart).build()

        world = Simulation.step(world)

        val actualPart = world.getPart(1,1, expectedPart.width, expectedPart.height)
        Assertions.assertEquals(expectedPart, actualPart)
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
        Assertions.assertEquals(expectedPart, actualPart)
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
        Assertions.assertEquals(expectedPart, actualPart)
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


        for (i in 0..<stepsToMoveByTwo) {
            world = Simulation.step(world)
        }

        val actualPart = world.getPart(initialX+2,initialY+2, gliderPart.width, gliderPart.height)
        Assertions.assertEquals(gliderPart, actualPart)
        Assertions.assertEquals(expectedChunks, world.chunks.size)
    }
}