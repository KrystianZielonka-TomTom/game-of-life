import org.example.Simulation
import org.example.WorldData
import org.example.deserializeCells
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class SimulationTest {

    @Test
    fun whenWalker_thenWalk() {
        val initialPart = deserializeCells("""
            ______
            __X___
            ___X__
            _XXX__
            ______
        """.trimIndent())

        val expectedPart = deserializeCells("""
            ______
            ______
            _X_X__
            __XX__
            __X___
        """.trimIndent())

        val world = WorldData()
        world.insertPart(1, 1, initialPart)
        val sim = Simulation(world)
        val simWorld = sim.step()
        val actualPart = simWorld.getPart(1,1, expectedPart.width, expectedPart.height)

        Assertions.assertEquals(expectedPart, actualPart)
    }

    @Test
    fun whenOneAlive_thenDie() {
        val initialPart = deserializeCells("""
            ______
            ______
            _X____
            ______
            ______
        """.trimIndent())

        val expectedPart = deserializeCells("""
            ______
            ______
            ______
            ______
            ______
        """.trimIndent())

        val world = WorldData()
        world.insertPart(1, 1, initialPart)
        val sim = Simulation(world)
        val simWorld = sim.step()
        val actualPart = simWorld.getPart(1,1, expectedPart.width, expectedPart.height)

        Assertions.assertEquals(expectedPart, actualPart)
    }

    @Test
    fun whenSquareOfFour_thenStayTheSame() {
        val initialPart = deserializeCells("""
            ______
            __XX__
            __XX__
            ______
            ______
        """.trimIndent())

        val expectedPart = deserializeCells("""
            ______
            __XX__
            __XX__
            ______
            ______
        """.trimIndent())

        val world = WorldData()
        world.insertPart(1, 1, initialPart)
        val sim = Simulation(world)
        val simWorld = sim.step()
        val actualPart = simWorld.getPart(1,1, expectedPart.width, expectedPart.height)

        Assertions.assertEquals(expectedPart, actualPart)
    }

    @Test
    fun whenTooManyNeighbours_thenDieFromOverpopulation() {
        val initialPart = deserializeCells("""
            ______
            ___X__
            __XXX_
            ___X__
            ______
        """.trimIndent())

        val expectedPart = deserializeCells("""
            ______
            __XXX_
            __X_X_
            __XXX_
            ______
        """.trimIndent())

        val world = WorldData()
        world.insertPart(1, 1, initialPart)
        val sim = Simulation(world)
        val simWorld = sim.step()
        val actualPart = simWorld.getPart(1,1, expectedPart.width, expectedPart.height)

        Assertions.assertEquals(expectedPart, actualPart)
    }
}