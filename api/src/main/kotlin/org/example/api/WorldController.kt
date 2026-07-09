package org.example.api

import org.example.api.dto.CellPartDto
import org.example.api.dto.WorldDto
import org.example.api.request.WorldStepRequest
import org.springframework.web.bind.annotation.*

@RestController
class WorldController(private val worldService: WorldService) {

    @CrossOrigin
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
    ): CellPartDto {
        //Application logic in controller

        val part = worldService.getRandomWorld(steps,
            initialX,
            initialY,
            initialWidth,
            initialHeight,
            requestedX,
            requestedY,
            requestedWidth,
            requestedHeight,
            if(seed==-1L) null else seed)
        return CellPartDto(requestedX, requestedY, part.width, part.height, part.data)
    }

    @CrossOrigin
    @PostMapping("/world/step")
    fun getNextState(@RequestBody request: WorldStepRequest): WorldDto {
        return worldService.getNextState(request)
    }
}