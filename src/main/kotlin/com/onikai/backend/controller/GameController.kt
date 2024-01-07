package com.onikai.backend.controller


import com.onikai.backend.extensions.Auth
import com.onikai.backend.model.enity.Game
import com.onikai.backend.service.GameService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.Instant

@RestController
@RequestMapping("/api/v1/games")
class GameController(
  val gameService: GameService
) {
  @GetMapping
  fun index(
    auth: Auth,
    @RequestParam("timestamp") timestampStr: String?):List<Game> {
    val timestamp = timestampStr?.let { Instant.parse(it) } ?: Instant.EPOCH
    return gameService.retrieveGamesFor(123, timestamp)
  }
}