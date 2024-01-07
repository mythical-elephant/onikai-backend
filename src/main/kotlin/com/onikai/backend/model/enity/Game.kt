package com.onikai.backend.model.enity

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import org.hibernate.annotations.JdbcTypeCode
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import java.time.Instant

@Entity
@Table(name = "games")
class Game {
  @Id
  @Column(name = "id", nullable = false, length = Integer.MAX_VALUE)
  var id: String? = null

  @Column(name = "home_id")
  var home: Long = null

  @Column(name = "away_id")
  var away: Long = null

  @Column(name = "creator_id")
  var creator: Long = null

  @Column(name = "winner_id")
  var winner: Long? = null

  @Column(name = "home_team", length = Integer.MAX_VALUE)
  var homeTeam: String = null

  @Column(name = "away_team", length = Integer.MAX_VALUE)
  var awayTeam: String = null

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "data")
  var data: MutableMap<String, Any>? = null

  @Column(name = "turn")
  var turn: Int? = null

  @Column(name = "ranked")
  var ranked: Boolean? = null

  @Column(name = "completed")
  var completed: Boolean? = null

  @Column(name = "started")
  var started: Boolean? = null

  @NotNull
  @Column(name = "created_at", nullable = false)
  var createdAt: Instant? = null

  @NotNull
  @Column(name = "updated_at", nullable = false)
  var updatedAt: Instant? = null

  @NotNull
  @Column(name = "level", nullable = false, length = Integer.MAX_VALUE)
  var level: String? = null

  @NotNull
  @Column(name = "is_guest_game", nullable = false)
  var isGuestGame: Boolean? = false


}