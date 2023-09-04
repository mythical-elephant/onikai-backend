package com.onikai.backend

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.json.JsonMapper
import com.onikai.backend.support.factory.RegisterRequestFactory
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
  @Autowired
  private lateinit var mockMvc:MockMvc
  @Autowired
  private lateinit var registerRequestFactory: RegisterRequestFactory
  @Autowired
  private lateinit var jsonMapper: ObjectMapper
  @Test
  fun `Can register a user`() {
    // Given valid information
    val info = registerRequestFactory.create()

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
  fun `When a username cannot be registered an error is returned`() {
    // Given valid information
    val info = registerRequestFactory.create(password = "0")

    // When a user attempts to register
    // Their account is created
    mockMvc.perform(
      post("/api/v1/auth/register")
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonMapper.writeValueAsString(info))
    )
      .andExpect(status().isBadRequest)
      .andReturn()
      .let {
        assertEquals(it.response.errorMessage, "Password must be at least 6 characters")
      }
  }
}