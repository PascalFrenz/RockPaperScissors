package me.frenz.backend

import me.frenz.backend.Hand.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*
import kotlin.random.Random

@RestController
@RequestMapping("/game")
class GameController(
    @Autowired val gameEngine: GameEngine,
    @Autowired val gameRepository: GameRepository,
    @Autowired val gameHistoryRepository: GameHistoryRepository
) {

    companion object {
        val POSSIBLE_HANDS = arrayOf(ROCK, PAPER, SCISSORS)
    }

    @PostMapping("/{gameId}/play", consumes = [MediaType.TEXT_PLAIN_VALUE])
    fun play(@PathVariable gameId: Long, @RequestBody playerHandValue: String): Optional<RoundResultDTO> {
        val playerHand: Hand = Hand.fromString(playerHandValue)
        if (INVALID == playerHand) {
            throw ResponseStatusException(
                HttpStatus.UNPROCESSABLE_ENTITY,
                "Invalid Hand. Cannot process $playerHandValue"
            )
        }

        val maybeGame = gameRepository.findById(gameId)
        if (maybeGame.isEmpty) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find game with id $gameId")
        }

        val opponentHand = POSSIBLE_HANDS[Random.nextInt(0, POSSIBLE_HANDS.size)]
        val winner = gameEngine.computeWinner(playerHand, opponentHand)

        val history = gameHistoryRepository.save(
            GameHistory(null, maybeGame.get(), playerHand, opponentHand, winner)
        )

        return Optional.of(RoundResultDTO.fromHistory(history))
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createGame(): Game = gameRepository.save(Game(null))

    @GetMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun listGames(): List<Game> = gameRepository.findAll().toList()

    @DeleteMapping("/{gameId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun deleteGame(@PathVariable gameId: Long): Unit = gameRepository.deleteById(gameId)

    @GetMapping("/{gameId}/history")
    fun getGameHistory(@PathVariable gameId: Long): List<RoundResultDTO> = gameHistoryRepository
        .findAllByGameId(gameId)
        .map(RoundResultDTO.Companion::fromHistory)

}
