package org.example.domain

@JvmInline
value class ChunkLocalVector2D(private val v: Vector2D) {
    constructor(x: Int, y: Int) : this(Vector2D(x, y))
    val x: Int
        get() = v.x

    val y: Int
        get() = v.y

    override fun toString(): String {
        return "ChunkLocalVector2D(y=$y, x=$x)"
    }
}

@JvmInline
value class ChunkIndexVector2D(private val v: Vector2D) {
    constructor(x: Int, y: Int) : this(Vector2D(x, y))
    val x: Int
        get() = v.x

    val y: Int
        get() = v.y

    override fun toString(): String {
        return "ChunkIndexVector2D(y=$y, x=$x)"
    }
}

@JvmInline
value class GlobalVector2D(private val v: Vector2D) {
    constructor(x: Int, y: Int) : this(Vector2D(x, y))
    val x: Int
        get() = v.x

    val y: Int
        get() = v.y

    override fun toString(): String {
        return "GlobalVector2D(y=$y, x=$x)"
    }
}