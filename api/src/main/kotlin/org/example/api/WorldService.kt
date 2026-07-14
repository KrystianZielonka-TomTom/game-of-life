package org.example.api

import org.example.api.dto.TimingDto
import org.example.api.dto.WorldDto
import org.example.api.mappers.TileMapper
import org.example.api.request.WorldRandomRequest
import org.example.api.request.WorldStepRequest
import org.example.domain.CellPart
import org.example.domain.GlobalVector2D
import org.example.domain.Vector2D
import org.example.domain.World
import org.springframework.stereotype.Service
import kotlin.random.Random
import kotlin.system.measureTimeMillis

@Service
class WorldService(private val tileMapper: TileMapper) {

    fun getRandomWorld(request: WorldRandomRequest): TimingDto<WorldDto> {
        var actualSeed = request.seed
        if (request.seed==null) actualSeed = System.currentTimeMillis()

        var world: World
        val loadMs = measureTimeMillis {
            world =  World.fromRandom(
                GlobalVector2D(request.initialX, request.initialY),
                Vector2D(request.initialWidth, request.initialHeight),
                Random(actualSeed)
            )
        }
        val stepMs = measureTimeMillis {
            world = world.step(request.steps)
        }
        return TimingDto(
            stepMs.toInt(),
            loadMs.toInt(),
            WorldDto(world.getTiles().map { tileMapper.fromTile(it) })
        )
    }

    fun getNextState(request: WorldStepRequest): TimingDto<WorldDto> {
        var world: World
        val loadMs = measureTimeMillis {
            world = World.fromTiles(request.world.tiles.map { tileMapper.toTile(it) })
        }

        val stepMs = measureTimeMillis {
            world = world.step(request.step)
        }

        return TimingDto(
            stepMs.toInt(),
            loadMs.toInt(),
            WorldDto(world.getTiles().map { tileMapper.fromTile(it) })
        )
    }
}