package me.frenz.backend

import me.frenz.backend.Hand.*
import me.frenz.backend.RoundWinner.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class GameEngineTest {
    private val gameEngine = GameEngine()

    @Test
    fun givenRock_whenAgainstPaper_shouldLoose() {
        Assertions.assertEquals(OPPONENT, gameEngine.computeWinner(ROCK, PAPER))
    }

    @Test
    fun givenRock_whenAgainstScissors_shouldWin() {
        Assertions.assertEquals(PLAYER, gameEngine.computeWinner(ROCK, SCISSORS))
    }

    @Test
    fun givenRock_whenAgainstRock_shouldDraw() {
        Assertions.assertEquals(DRAW, gameEngine.computeWinner(ROCK, ROCK))
    }

    @Test
    fun givenPaper_whenAgainstScissors_shouldLoose() {
        Assertions.assertEquals(OPPONENT, gameEngine.computeWinner(PAPER, SCISSORS))
    }

    @Test
    fun givenPaper_whenAgainstRock_shouldWin() {
        Assertions.assertEquals(PLAYER, gameEngine.computeWinner(PAPER, ROCK))
    }

    @Test
    fun givenPaper_whenAgainstPaper_shouldDraw() {
        Assertions.assertEquals(DRAW, gameEngine.computeWinner(PAPER, PAPER))
    }
}
