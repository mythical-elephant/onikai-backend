package com.onikai.backend

import com.onikai.backend.controller.authentication.RegisterRequest
import com.onikai.backend.repository.UserRepository
import com.onikai.backend.service.AuthenticationService
import com.onikai.backend.support.DatabaseCleaner
import com.onikai.backend.support.factory.UserFactory
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.containsString
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension


@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension::class)
@AutoConfigureMockMvc
class AuthenticationServiceTests : DatabaseCleaner {
  @Autowired
  private lateinit var authenticationService: AuthenticationService
  @Autowired
  private lateinit var userRepository: UserRepository
  @Autowired
  private lateinit var userFactory: UserFactory

  @Autowired
  override lateinit var jdbcTemplate: JdbcTemplate

  @Test
  fun `Register fails if username is too long`() {
    val existingUser = userFactory.create(username = "ALongUserNameALongUserNameALongUsername", save = false)

    val exception = assertThrows<Exception> {
      authenticationService.register(RegisterRequest(
        username = existingUser.username!!,
        password = existingUser.password!!,
        email = existingUser.email!!
      ))
    }

    assertThat(exception.message!!, containsString("Username must be between 3 and 20"))
  }

  @Test
  fun `Register fails if username is taken`() {
    val existingUser = userFactory.create(save = true)
    val newUser = userFactory.create(username = existingUser.username!!, save = false)

    val exception = assertThrows<Exception> {
      authenticationService.register(RegisterRequest(
        username = newUser.username!!,
        password = newUser.password!!,
        email = newUser.email!!
      ))
    }

    assertThat(exception.message!!, containsString("Username is a already taken"))
  }

  @Test
  fun `Register fails if username is a reserved word`() {
    val existingUser = userFactory.create(username = "signin", save = false)

    val exception = assertThrows<Exception> {
      authenticationService.register(RegisterRequest(
        username = existingUser.username!!,
        password = existingUser.password!!,
        email = existingUser.email!!
      ))
    }

    assertThat(exception.message!!, containsString("Invalid username 'signin'"))
  }

  @Test
  fun `When everything is correct, then a user is created`() {
    val user = userFactory.create(save = false)

    authenticationService.register(RegisterRequest(
      username = user.username!!,
      password = user.password!!,
      email = user.email!!
    ))

    assertNotNull(userRepository.existsByUsername(user.username!!))
  }
}