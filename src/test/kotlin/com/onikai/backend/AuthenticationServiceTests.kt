package com.onikai.backend

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.json.JsonMapper
import com.onikai.backend.controller.authentication.RegisterRequest
import com.onikai.backend.service.AuthenticationService
import com.onikai.backend.support.factory.RegisterRequestFactory
import com.onikai.backend.support.factory.UserFactory
import org.junit.experimental.results.ResultMatchers
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
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
class AuthenticationServiceTests {
  @Autowired
  private lateinit var authenticationService: AuthenticationService
  @Autowired
  private lateinit var userFactory: UserFactory
  @Test
  fun `Register fails if username is too long`() {
    val existingUser = userFactory.create(username = "Avary Long UsernameIndeed", save = true)
    val newUser = userFactory.create(username = existingUser._username!!, save = false)

    val exception = assertThrows<Exception> {
      authenticationService.register(RegisterRequest(
        username = newUser.username,
        password = newUser._password!!,
        email = newUser.email!!
      ))
    }

    assertTrue(exception.message!!.contains("Username must <= 16 characters"))
  }

  @Test
  fun `Register fails if username is taken`() {
    val existingUser = userFactory.create(save = true)
    val newUser = userFactory.create(username = existingUser._username!!, save = false)

//    val exception = assertThrows<Exception> {
      authenticationService.register(RegisterRequest(
        username = newUser.username,
        password = newUser._password!!,
        email = newUser.email!!
      ))
//    }

//    assertTrue(exception.message!!.contains("Username must <= 16 characters"))
  }
}