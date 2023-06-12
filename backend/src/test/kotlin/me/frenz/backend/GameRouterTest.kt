package me.frenz.backend

import me.frenz.backend.Hand.PAPER
import me.frenz.backend.Hand.ROCK
import me.frenz.backend.RoundWinner.*
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.*
import org.springframework.web.servlet.function.RequestPredicates.contentType
import java.util.*

@ExtendWith(SpringExtension::class)
@WebMvcTest(controllers = [GameController::class])
@Import(GameEngine::class)
class GameRouterTest(
    @Autowired val mockMvc: MockMvc
) {

    @MockBean
    private lateinit var gameRepository: GameRepository

    @MockBean
    private lateinit var gameHistoryRepository: GameHistoryRepository

    @Test
    fun givenCorrectHand_whenCallingPlay_shouldReturnSuccessfully() {
        val game = Game(0)
        `when`(gameRepository.findById(0)).thenReturn(Optional.of(game))
        `when`(gameHistoryRepository.save(any())).thenReturn(GameHistory(0, game, ROCK, PAPER, OPPONENT))

        mockMvc.post("/game/0/play") {
            contentType = MediaType.TEXT_PLAIN
            content = "ROCK"
        }.andExpect {
            status { isOk() }
            contentType(MediaType.APPLICATION_JSON)
            content {
                jsonPath("$.humanRoundValue") { value("ROCK") }
                jsonPath("$.pcRoundValue") { value("PAPER") }
                jsonPath("$.winner") { value("OPPONENT") }
            }
        }
    }

    @Test
    fun givenNonexistentGameId_whenCallingPlay_shouldReturnNotFound() {
        `when`(gameRepository.findById(0)).thenReturn(Optional.empty())
        mockMvc.post("/game/0/play") {
            contentType = MediaType.TEXT_PLAIN
            content = "ROCK"
        }.andExpect {
            status { isNotFound() }
        }
    }

    @Test
    fun givenInvalidHand_whenCallingPlay_shouldReturnUnprocessableEntity() {
        mockMvc.post("/game/0/play") {
            contentType = MediaType.TEXT_PLAIN
            content = "SOMETHING_INVALID"
        }.andExpect {
            status { isUnprocessableEntity() }
        }
    }

    @Test
    fun givenInvalidGameId_whenCallingPlay_shouldReturnBadRequest() {
        mockMvc.post("/game/INVALID_ID/play") {
            contentType = MediaType.TEXT_PLAIN
            content = "ROCK"
        }.andExpect {
            status { isBadRequest() }
        }
    }

    @Test
    fun givenNothing_whenCallingGamePut_shouldCreateNewGame() {
        `when`(gameRepository.save(any(Game::class.java))).thenReturn(Game(0))

        mockMvc.put("/game")
            .andExpect {
                status { isCreated() }
                contentType(MediaType.APPLICATION_JSON)
                content {
                    jsonPath("$.id") { value(0) }
                }
            }
    }

    @Test
    fun givenId_whenCallingGameDelete_shouldDeleteGame() {
        mockMvc.delete("/game/0")
            .andExpect {
                status { isAccepted() }
                contentType(MediaType.APPLICATION_JSON)
                content { string("") }
            }
    }

    @Test
    fun givenId_whenCallingGameHistory_shouldReturnListOfHistory() {
        `when`(gameHistoryRepository.findAllByGameId(0)).thenReturn(listOf(
            GameHistory(0, Game(0), ROCK, PAPER, OPPONENT),
            GameHistory(1, Game(0), ROCK, PAPER, OPPONENT),
            GameHistory(2, Game(0), ROCK, PAPER, OPPONENT)
        ))

        mockMvc.get("/game/0/history")
            .andExpect {
                status { isOk() }
                contentType(MediaType.APPLICATION_JSON)
                content {
                    jsonPath("$.[0].id", equalTo(0))
                    jsonPath("$.[1].id", equalTo(1))
                    jsonPath("$.[2].id", equalTo(2))
                }
            }
    }
}
