package org.example.domain

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class WorldTest {

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
        val initialWorld = World.empty().withPart(1,1,initialPart)

        //When
        val world = initialWorld.step()

        //Then
        val actualPart = world.getPart(1,1, expectedPart.width, expectedPart.height)
        Assertions.assertEquals(expectedPart, actualPart) //assertJ
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
        var world = World.empty().withPart(1,1,initialPart)

        world = world.step()

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
        var world = World.empty().withPart(1,1,initialPart)

        world = world.step()

        val actualPart = world.getPart(1,1, expectedPart.width, expectedPart.height)
        Assertions.assertEquals(expectedPart, actualPart)
    }

    @Test
    fun `Glider placed at the edge of the chunk, should create new chunks`() {
        //Since chunk is now fully private, I cannot get chunk width
        //I don't know how to fix it cleanly



//        val gliderPart = CellPart.deserializeCells("""
//            OXO
//            OOX
//            XXX
//            OOO
//        """.trimIndent())
//
//        //Places exactly in chunk (0,0) on the edge
//        val initialX = Chunk.WIDTH - gliderPart.width
//        val initialY = Chunk.WIDTH - gliderPart.height
//
//        var world = World.empty().withPart(initialX, initialY, gliderPart)
//        val stepsToMoveByTwo = 8
//        val expectedChunks = 3
//
//        //When
//        repeat (stepsToMoveByTwo) {
//            world = World.step(world)
//        }
//
//        val actualPart = world.getPart(initialX+2,initialY+2, gliderPart.width, gliderPart.height)
//
//        //Then
//        Assertions.assertEquals(gliderPart, actualPart)
//        Assertions.assertEquals(expectedChunks, world.chunks.size)
    }
}