package me.frenz.backend

import org.springframework.data.repository.CrudRepository


interface GameRepository : CrudRepository<Game, Long>

interface GameHistoryRepository : CrudRepository<GameHistory, Long> {

    fun findAllByGameId(gameId: Long): List<GameHistory>
}
