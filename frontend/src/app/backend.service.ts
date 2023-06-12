import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Game, Hand, RoundResultDTO} from "./types";


const API_URL = "http://localhost:8080"

@Injectable({
  providedIn: 'root'
})
export class BackendService {

  constructor(private http: HttpClient) {}

  playRound(hand: Hand, gameId: number) {
    return this.http.post<RoundResultDTO>(`${API_URL}/game/${gameId}/play`, hand.valueOf());
  }

  createGame() {
    return this.http.put<Game>(`${API_URL}/game`, {});
  }

  fetchAllGameIds() {
    return this.http.get<Array<Game>>(`${API_URL}/game`);
  }

  loadGameHistory(gameId: number) {
    return this.http.get<Array<RoundResultDTO>>(`${API_URL}/game/${gameId}/history`)
  }
}
