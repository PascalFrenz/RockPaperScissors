import {Component, OnInit, signal, WritableSignal} from '@angular/core';
import {Game, Hand, RoundResultDTO} from "./types";
import {BackendService} from "./backend.service";

type Score = {
  computer: number;
  person: number;
}

@Component({
  selector: 'my-app',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  constructor(private backend: BackendService) {
  }

  scores: WritableSignal<Score> = signal({
    computer: 0,
    person: 0
  });

  history: WritableSignal<Array<RoundResultDTO>> = signal([]);
  games: WritableSignal<Array<Game>> = signal([]);
  currentGame: WritableSignal<Game | undefined> = signal(undefined);

  ngOnInit() {
    this.loadExistingGames();
  }

  loadExistingGames() {
    this.backend.fetchAllGameIds().subscribe({
      next: (games: Array<Game>) => {
        if (games.length > 0) {
          const game = games.at(-1)!;
          this.backend.loadGameHistory(game.id).subscribe({
            next: (history: Array<RoundResultDTO>) => this.setGame(game, history)
          })
        }
        this.games.set(games);
      }
    })
  }

  play(choice: Hand) {
    if (this.currentGame() === undefined) {
      console.error("Cannot play, no game active!");
      return;
    }
    this.backend.playRound(choice, this.currentGame()!.id).subscribe({
      next: (roundResult: RoundResultDTO) => {
        this.history.mutate(value => value.push(roundResult))
        this.scores.update(old => this.calcScore(roundResult, old))
      }
    });
  }

  newGame() {
    this.backend.createGame().subscribe({
      next: (game: Game) => this.setGame(game, []),
    })
  }

  selectGame(event: Event) {
    const gameId = parseInt((event.target as HTMLOptionElement).value);
    this.backend.loadGameHistory(gameId).subscribe({
      next: (history: Array<RoundResultDTO>) => this.setGame({id: gameId}, history)
    })
  }

  setGame(game: Game, history: Array<RoundResultDTO>) {
    this.currentGame.set(game);
    this.history.set(history)
    const score = history.reduce(
      (previousValue, currentValue) => this.calcScore(currentValue, previousValue),
      {person: 0, computer: 0} as Score
    )
    this.scores.set(score)
  }

  calcScore(result: RoundResultDTO, oldScore: Score): Score {
    if (result.winner == "PLAYER") {
      return {computer: oldScore.computer, person: oldScore.person + 1}
    } else if (result.winner == "OPPONENT") {
      return {computer: oldScore.computer + 1, person: oldScore.person};
    } else {
      return oldScore;
    }
  }
}
