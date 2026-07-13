package org.example.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Vector2DTest {

    @Test
    fun `vector2d given small x and y should return valid x and y`() {
        val x = 123;
        val y = 456;
        val vector = Vector2D(x, y)
        val actualX = vector.x
        val actualY = vector.y
        assertEquals(x, actualX)
        assertEquals(y, actualY)
    }

    @Test
    fun `vector2d given min and max values of int should return valid x and y`() {
        val x = Int.MIN_VALUE;
        val y = Int.MAX_VALUE;
        val vector = Vector2D(x, y)
        val actualX = vector.x
        val actualY = vector.y
        assertEquals(x, actualX)
        assertEquals(y, actualY)
    }
}