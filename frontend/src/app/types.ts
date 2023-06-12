export type Hand = "ROCK" | "PAPER" | "SCISSORS"

enum RoundWinner {
  PLAYER = "PLAYER",
  OPPONENT = "OPPONENT",
  DRAW = "DRAW"
}

export type RoundResultDTO = {
  humanRoundValue?: Hand
  pcRoundValue?: Hand
  winner?: RoundWinner
}
export type Game = { id: number };
