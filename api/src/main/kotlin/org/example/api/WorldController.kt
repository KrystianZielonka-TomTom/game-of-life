package org.example.api

import org.example.domain.World
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import kotlin.random.Random

@RestController
class WorldController {

    @GetMapping("/world/random")
    fun getRandomWorld(
        @RequestParam("steps", defaultValue = "1") steps: Int,
        @RequestParam("seed", defaultValue = "-1") seed: Long,
        @RequestParam("x", defaultValue = "0") initialX: Int,
        @RequestParam("y", defaultValue = "0") initialY: Int,
        @RequestParam("w", defaultValue = "100") initialWidth: Int,
        @RequestParam("h", defaultValue = "100") initialHeight: Int,
        @RequestParam("reqX", defaultValue = "0") requestedX: Int,
        @RequestParam("reqY", defaultValue = "0") requestedY: Int,
        @RequestParam("reqW", defaultValue = "100") requestedWidth: Int,
        @RequestParam("reqH", defaultValue = "100") requestedHeight: Int,
    ): WorldResponse {
        //Application logic in controller

        var actualSeed = seed
        if (seed==-1L) actualSeed = System.currentTimeMillis()

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

        return WorldResponse(requestedX, requestedY, part.width, part.height, part.data)
    }
}