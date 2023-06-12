package me.frenz.backend

import me.frenz.backend.Hand.*
import me.frenz.backend.RoundWinner.*
import org.springframework.stereotype.Service

@Service
class GameEngine {

    fun computeWinner(player: Hand, opponent: Hand): RoundWinner {
        return when (player) {
            ROCK -> when (opponent) {
                ROCK -> DRAW
                PAPER -> OPPONENT
                SCISSORS -> PLAYER
                INVALID -> throw IllegalStateException("Should never happen..")
            }

            PAPER -> when (opponent) {
                ROCK -> PLAYER
                PAPER -> DRAW
                SCISSORS -> OPPONENT
                INVALID -> throw IllegalStateException("Should never happen..")
            }

            SCISSORS -> when (opponent) {
                ROCK -> OPPONENT
                PAPER -> PLAYER
                SCISSORS -> DRAW
                INVALID -> throw IllegalStateException("Should never happen..")
            }
            INVALID -> throw IllegalStateException("Should never happen..")
        }
    }

}
