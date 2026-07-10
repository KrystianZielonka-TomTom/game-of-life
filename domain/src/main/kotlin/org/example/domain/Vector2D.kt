package org.example.domain

@JvmInline
value class Vector2D(private val l: Long) {
    constructor(x: Int, y: Int) : this(
        (x.toLong() shl 32) or (y.toLong() and 0xffffffffL)
    )

    val x: Int
        get() = (l shr 32).toInt()

    val y: Int
        get() = l.toInt()

    override fun toString(): String {
        return "Vector2D(y=$y, x=$x)"
    }
}