package com.onikai.backend.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.onikai.backend.controller.authentication.AuthenticationRequestDTO
import com.onikai.backend.model.enity.UserPrincipal
import com.onikai.backend.support.factory.UserFactory
import io.github.serpro69.kfaker.Faker
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.*
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user


@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class GameControllerTests {
  @Autowired private lateinit var mockMvc:MockMvc
  @Autowired private lateinit var userFactory: UserFactory
  @Autowired private lateinit var jsonMapper: ObjectMapper

  private val faker = Faker()

  @BeforeEach
  fun prepare() {
    SecurityContextHolder.clearContext()
  }

  @Test
  fun `Can retrieve a list of games`() {
    val user = userFactory.create(save = true)
    val principal = UserPrincipal(user)

    mockMvc
      .perform(
        get("/api/v1/games")
          .header("session_id", faker.random.randomString(8))
          .contentType(MediaType.APPLICATION_JSON)
          .with(user(principal)) // Use the user post-processor
      )

      .andExpect(status().isOk)
  }
}
