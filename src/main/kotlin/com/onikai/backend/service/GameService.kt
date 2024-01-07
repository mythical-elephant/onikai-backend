package com.onikai.backend.service

import com.onikai.backend.model.enity.Game
import com.onikai.backend.model.enity.User
import com.onikai.backend.repository.GameRepository
import org.springframework.stereotype.Service
import java.time.Instant


@Service
class GameService(
  val gameRepository: GameRepository
) {
  fun retrieveGamesFor(userId:Long, since:Instant):List<Game> {
    return gameRepository.findByHomeOrAwayAndUpdatedAtGreaterThan(userId, userId, since)
  }
}