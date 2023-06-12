package me.frenz.backend

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne

@Entity
class GameHistory(
    @Id @GeneratedValue var id: Long?,
    @ManyToOne var game: Game,
    var playerHand: Hand,
    var opponentHand: Hand,
    var winner: RoundWinner
)
