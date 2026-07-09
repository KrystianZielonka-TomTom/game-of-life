package org.example.api

import org.example.api.dto.TileDto
import org.example.domain.Tile

class TileMapper {
    fun fromTile(tile: Tile): TileDto {
        return TileDto(tile.tileIndexX, tile.tileIndexY, tile.cells)
    }
    fun toTile(tileDto: TileDto): Tile {
        return Tile(tileDto.tileIndexX, tileDto.tileIndexY, tileDto.data)
    }
}