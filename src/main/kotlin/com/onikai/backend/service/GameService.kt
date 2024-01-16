package com.onikai.backend.service

import InvalidGameStateException
import com.onikai.backend.model.enity.Game
import com.onikai.backend.model.enity.GameSummary
import com.onikai.backend.model.enity.Team
import com.onikai.backend.model.enity.User
import com.onikai.backend.repository.GameRepository
import org.springframework.stereotype.Service
import java.time.Instant
import kotlin.jvm.optionals.getOrNull
import kotlin.random.Random


@Service
class GameService(
  val gameRepository: GameRepository
) {
  fun create(user:User, team: Team, ranked:Boolean): Game {
    val game = Game()
    game.creator = user.id!!
    game.level = "level-0";
    game.isGuestGame = user.isGuest
    game.ranked = ranked

    // Randomly assign the user as home or away
    if( Random.nextBoolean() ) {
      game.homeTeam = team.value
      game.home = user.id
    } else {
      game.awayTeam = team.value
      game.away = user.id
    }

    return gameRepository.save(game)
  }

  /**
   * Starts `game` setting the "other" player the provided values
   */
  fun start(game:Game, otherUser:User, otherTeam:Team):Game {
    if(game.started == true) {
      throw InvalidGameStateException("Game already started")
    }
    if(game.home == null) {
      game.home = otherUser.id
      game.homeTeam = otherTeam.value
    } else {
      game.away = otherUser.id
      game.awayTeam = otherTeam.value
    }
    game.started = true
    return gameRepository.save(game)
  }

  fun gameSummaryList(userId:Long, since:Instant):List<GameSummary> {
    return gameRepository.findByHomeOrAwayAndUpdatedAtGreaterThan(userId, userId, since)
  }

  fun getGameByIdBelongingTo(gameId:String, userId:Long):Game? {
    return gameRepository.findGameByIdBelongingTo(gameId, userId).getOrNull()
  }

  fun getFirstUnstartedRankedGameNotCreatedBy(userId:Long): Game? {
    return gameRepository.findByStartedFalseAndCreatorNotOrderByCreatedAtDesc(userId).getOrNull()
  }
}