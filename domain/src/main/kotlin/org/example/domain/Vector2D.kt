package org.example.domain

typealias ChunkLocalVector2D = Vector2D
typealias ChunkIndexVector2D = Vector2D
typealias GlobalVector2D = Vector2D

@JvmInline
value class Vector2D(private val l: Long) {
    constructor(x: Int, y: Int) : this(
        (x.toLong() shl 32) or ((y + x * 31).toLong() and 0xffffffffL)
    )

    val x: Int
        get() = (l shr 32).toInt()

    //Default way java handles long hashcode is by performing xor on 32 lower and 32 higher bits of long
    //In practice it meant that a lot of chunks had the same hashcode
    //This method makes it less likely for hashes to collide for close chunks
    val y: Int
        get() = l.toInt() - x * 31

    override fun toString(): String {
        return "Vector2D(y=$y, x=$x)"
    }
}