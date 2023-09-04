package com.onikai.backend

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.json.JsonMapper
import com.onikai.backend.controller.authentication.AuthenticationRequestDTO
import com.onikai.backend.support.factory.RegisterRequestFactory
import com.onikai.backend.support.factory.UserFactory
import io.github.serpro69.kfaker.Faker
import org.junit.experimental.results.ResultMatchers
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension::class)
@AutoConfigureMockMvc
@Transactional
class AuthenticationControllerTests {
  @Autowired private lateinit var mockMvc:MockMvc
  @Autowired private lateinit var registerRequestFactory: RegisterRequestFactory
  @Autowired private lateinit var userFactory: UserFactory
  @Autowired private lateinit var jsonMapper: ObjectMapper

  private val faker = Faker()

  @Test
  fun `Can register a user`() {
    // Given valid information
    val password = faker.random.randomString(8)
    val user = userFactory.create(password = password, save = false)
    val info = registerRequestFactory.create(user.username!!, user.email!!, password )

    // When a user attempts to register
    // Their account is created
    mockMvc.perform(
      post("/api/v1/auth/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonMapper.writeValueAsString(info))
    )
      .andExpect(status().isOk)
  }

  @Test
  fun `Can authenticate a user via username`() {
    // Given valid information
    val password = faker.random.randomString(8)
    val user = userFactory.create(password = password, save = true)
    val info = AuthenticationRequestDTO(user.username!!, password)

    // When a user attempts to register
    // Their account is created
    mockMvc.perform(
      post("/api/v1/auth/authenticate")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonMapper.writeValueAsString(info))
    )
      .andExpect(status().isOk)
  }


  @Test
  fun `Can authenticate a user via email address`() {
    // Given valid information
    val password = faker.random.randomString(8)
    val user = userFactory.create(password = password, save = true)
    val info = AuthenticationRequestDTO(user.email!!, password)

    // When a user attempts to register
    // Their account is created
    mockMvc.perform(
      post("/api/v1/auth/authenticate")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonMapper.writeValueAsString(info))
    )
      .andExpect(status().isOk)
  }

  @Test
  fun `Fails when an invalid password is provided`() {
    // Given valid information
    val password = faker.random.randomString(8)
    val user = userFactory.create(password = password, save = true)
    val info = AuthenticationRequestDTO(user.email!!, "wrongpassword")

    // When a user attempts to register
    mockMvc.perform(
      post("/api/v1/auth/authenticate")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonMapper.writeValueAsString(info))
    )
      .andExpect(status().isForbidden)
  }
}