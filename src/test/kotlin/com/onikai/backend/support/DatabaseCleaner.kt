package com.onikai.backend.support

import org.junit.jupiter.api.BeforeEach
import org.springframework.jdbc.core.JdbcTemplate

interface DatabaseCleaner {

  var jdbcTemplate: JdbcTemplate

  @BeforeEach
  fun resetDatabase() {
    jdbcTemplate.execute("TRUNCATE TABLE users")
  }
}