package org.example.api

import org.example.api.dto.WorldDto
import org.example.api.request.WorldStepRequest
import org.example.domain.CellPart
import org.example.domain.World
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class WorldService(private val tileMapper: TileMapper) {

    fun getRandomWorld(
        steps: Int,
        initialX: Int,
        initialY: Int,
        initialWidth: Int,
        initialHeight: Int,
        requestedX: Int,
        requestedY: Int,
        requestedWidth: Int,
        requestedHeight: Int,
        seed: Long? = null,
    ): CellPart {
        var actualSeed = seed
        if (seed==null) actualSeed = System.currentTimeMillis()

        val part = World.fromRandom(
            initialX,
            initialY,
            initialWidth,
            initialHeight,
            Random(actualSeed)
        ).step(steps)
            .getPart(
                requestedX,
                requestedY,
                requestedWidth,
                requestedHeight
            )

        return part;
    }

    fun getNextState(request: WorldStepRequest): WorldDto {
        val world = World.fromTiles(request.world.tiles.map { tileMapper.toTile(it) })
            .step(request.step)

        return WorldDto(world.getTiles().map { tileMapper.fromTile(it) })
    }
}