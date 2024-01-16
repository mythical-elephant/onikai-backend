package com.onikai.backend.repository;

import com.onikai.backend.model.enity.Game
import com.onikai.backend.model.enity.GameSummary
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.ListPagingAndSortingRepository
import java.time.Instant
import java.util.*

interface GameRepository : JpaRepository<Game, String> {

  /**
   * Returns all games where a user is either home or away. Optionally ask to return games "since" a specific time
   */
  fun findByHomeOrAwayAndUpdatedAtGreaterThan(home: Long, away: Long, updatedAt: Instant? = Instant.EPOCH): List<GameSummary>

  @Query("select g from Game g where g.id = ?1 and (g.home = ?2 or g.away = ?2)")
  fun findGameByIdBelongingTo(id: String, userId:Long): Optional<Game>

  @Query("select g from Game g where g.started = false and g.creator <> ?1 order by g.createdAt DESC")
  fun findByStartedFalseAndCreatorNotOrderByCreatedAtDesc(creator: Long): Optional<Game>

}