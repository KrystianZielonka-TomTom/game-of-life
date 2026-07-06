import org.example.SimConstants
import org.example.Simulation
import org.example.WorldBuilder
import org.example.WorldData
import org.example.deserializeCells
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class SimulationTest {

    @Test
    fun step_whenGliderPlaced_shouldMove() {
        val initialPart = deserializeCells("""
            _X__
            __X_
            XXX_
            ____
        """.trimIndent())

        val expectedPart = deserializeCells("""
            ____
            X_X_
            _XX_
            _X__
        """.trimIndent())
        var world = WorldBuilder().setPart(1,1, initialPart).build()

        world = Simulation.step(world)

        val actualPart = world.getPart(1,1, expectedPart.width, expectedPart.height)
        Assertions.assertEquals(expectedPart, actualPart)
    }

    @Test
    fun step_whenOneAlive_shouldDieFromUnderpopulation() {
        val initialPart = deserializeCells("""
            ____
            __X_
            ____
        """.trimIndent())

        val expectedPart = deserializeCells("""
            ____
            ____
            ____
        """.trimIndent())
        var world = WorldBuilder().setPart(1,1, initialPart).build()

        world = Simulation.step(world)

        val actualPart = world.getPart(1,1, expectedPart.width, expectedPart.height)
        Assertions.assertEquals(expectedPart, actualPart)
    }

    @Test
    fun step_whenStablePatternPlaced_shouldStayTheSame() {
        val initialPart = deserializeCells("""
            ____
            _XX_
            _XX_
            ____
        """.trimIndent())

        val expectedPart = deserializeCells("""
            ____
            _XX_
            _XX_
            ____
        """.trimIndent())
        var world = WorldBuilder().setPart(1,1, initialPart).build()

        world = Simulation.step(world)

        val actualPart = world.getPart(1,1, expectedPart.width, expectedPart.height)
        Assertions.assertEquals(expectedPart, actualPart)
    }

    @Test
    fun step_whenGliderPlacedNearChunkBorder_shouldCreateNewChunksOnMove() {
        val gliderPart = deserializeCells("""
            _X_
            __X
            XXX
            ___
        """.trimIndent())

        //Places exactly in chunk (0,0) on the edge
        val initialX = SimConstants.CHUNK_SIZE-gliderPart.width
        val initialY = SimConstants.CHUNK_SIZE-gliderPart.height

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