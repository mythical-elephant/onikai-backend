package com.onikai.backend.controller


import com.onikai.backend.extensions.user
import com.onikai.backend.model.enity.Game
import com.onikai.backend.model.enity.GameSummary
import com.onikai.backend.model.enity.Team
import com.onikai.backend.model.enity.UserPrincipal
import com.onikai.backend.service.GameService
import com.onikai.backend.service.RedisService
import com.onikai.backend.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.*
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.security.Principal
import java.time.Instant



@RestController
@RequestMapping("/api/v1/games")
class GameController(
  private val gameService: GameService,
  private val userService: UserService,
  private val redisService: RedisService
) {

  // GET /api/v1/games
  @GetMapping
  fun index(
    @RequestHeader("session_id") sessionId: String,
    @RequestParam("timestamp") timestampStr: String?,
    authToken: UsernamePasswordAuthenticationToken
  ):ResponseEntity<List<GameSummary>> {
    val timestamp = timestampStr?.let { Instant.parse(it) } ?: Instant.EPOCH
    val key = "/games/index/${authToken.user.id!!}/${sessionId}"

    if(redisService.hasValue(key)){
      val value = gameService.gameSummaryList(authToken.user.id!!, timestamp)
      redisService.setValue(key, true)
      return ResponseEntity.ok(value)
    }

    return ResponseEntity.ok(emptyList())
  }

  /**
   * Returns a game to the player if they are one of the players
   */
  @GetMapping("/api/v1/games/{gameId}")
  fun getGame(
    @PathVariable gameId: String,
    auth: UserPrincipal
  ):Game? {
    return gameService.getGameByIdBelongingTo(gameId = gameId, userId = auth.id!!)
  }

  /**
   * Returns a game to the player if they are one of the players
   */
  @PostMapping("/api/v1/games")
  fun create(
    @RequestParam ranked: Boolean,
    @RequestParam teamStr: String,
    auth: UserPrincipal
  ):Game? {
    val user = userService.find(auth.id!!) ?: throw ResponseStatusException(BAD_REQUEST, "Not a valid user")
    val team = Team.valueOf(teamStr)

    // Wants a ranked game, see if there is a ranked game in the queue
    if(ranked) { //
      val existingGame = gameService.getFirstUnstartedRankedGameNotCreatedBy(auth.id!!)
      if(existingGame != null) {
        gameService.start(existingGame, otherUser = user, otherTeam = team)
        return existingGame
      }
    }

    // No ranked game in the queue, or didn't want a ranked game
    // Create a new game
    return gameService.create(user = user, team = team, ranked = ranked)
  }
}