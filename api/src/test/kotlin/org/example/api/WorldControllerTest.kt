package org.example.api

import io.restassured.RestAssured
import io.restassured.http.ContentType
import org.example.api.dto.WorldDto
import org.example.api.mappers.TileMapper
import org.example.api.request.WorldStepRequest
import org.example.domain.CellPart
import org.example.domain.World
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import tools.jackson.databind.ObjectMapper

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WorldControllerTest {
    @LocalServerPort
    private var port: Int = 0

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var tileMapper: TileMapper

    @BeforeEach
    fun setUp() {
        RestAssured.port = port
    }

    @Test
    fun `random world request should return valid CellPartDto`() {
        RestAssured.given()
            .queryParam("steps", 1)
            .queryParam("x", 0)
            .queryParam("y", 0)
            .queryParam("w", 50)
            .queryParam("h", 50)
            .queryParam("reqX", 0)
            .queryParam("reqY", 0)
            .queryParam("reqW", 50)
            .queryParam("reqH", 50)
        .`when`()
            .get("/world/random")
        .then()
        .statusCode(200)
            .contentType(ContentType.JSON)
            .body("x", equalTo(0))
            .body("y", equalTo(0))
            .body("width", equalTo(50))
            .body("height", equalTo(50))
            .body("data", notNullValue())
    }

    @Test
    fun `next state request should return valid WorldDto`() {
        val part = CellPart.deserializeCells("""
            OOOO
            OXXO
            OXXO
            OOOO
        """.trimIndent())
        val requestBody = WorldStepRequest(1, WorldDto(
            World.empty()
                .withPart(1, 1, part)
                .getTiles()
                .map { tileMapper.fromTile(it) }))


        RestAssured.given()
            .body(objectMapper.writeValueAsString(requestBody))
            .contentType(ContentType.JSON)
            .`when`()
            .post("/world/step")
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("tiles.size()", equalTo(1))
    }
}