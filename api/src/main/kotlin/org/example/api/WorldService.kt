package org.example.api

import org.example.api.dto.WorldDto
import org.example.api.request.WorldRandomRequest
import org.example.api.request.WorldStepRequest
import org.example.domain.CellPart
import org.example.domain.World
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class WorldService(private val tileMapper: TileMapper) {

    fun getRandomWorld(request: WorldRandomRequest): CellPart {
        var actualSeed = request.seed
        if (request.seed==null) actualSeed = System.currentTimeMillis()

        val part = World.fromRandom(
            request.initialX,
            request.initialY,
            request.initialWidth,
            request.initialHeight,
            Random(actualSeed)
        ).step(request.steps)
            .getPart(
                request.requestedX,
                request.requestedY,
                request.requestedWidth,
                request.requestedHeight
            )

        return part;
    }

    fun getNextState(request: WorldStepRequest): WorldDto {
        val world = World.fromTiles(request.world.tiles.map { tileMapper.toTile(it) })
            .step(request.step)

        return WorldDto(world.getTiles().map { tileMapper.fromTile(it) })
    }
}