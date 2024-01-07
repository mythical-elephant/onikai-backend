package com.onikai.backend.repository;

import com.onikai.backend.model.enity.Game
import org.springframework.data.repository.ListPagingAndSortingRepository
import java.time.Instant

interface GameRepository : ListPagingAndSortingRepository<Game, String> {

  /**
   * Returns all games where a user is either home or away. Optionally ask to return games "since" a specific time
   */
  fun findByHomeOrAwayAndUpdatedAtGreaterThan(home: Long, away: Long, updatedAt: Instant? = Instant.EPOCH): List<Game>

}