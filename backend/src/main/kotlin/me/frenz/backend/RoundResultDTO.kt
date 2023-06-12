package me.frenz.backend

class RoundResultDTO(
    val humanRoundValue: Hand,
    val pcRoundValue: Hand,
    val winner: RoundWinner
) {
    companion object {
        fun fromHistory(history: GameHistory): RoundResultDTO = RoundResultDTO(
            history.playerHand,
            history.opponentHand,
            history.winner
        )
    }
}
