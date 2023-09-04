package com.onikai.backend.support.factory

import com.onikai.backend.model.enity.User
import com.onikai.backend.model.enum.Role
import com.onikai.backend.repository.UserRepository
import io.github.serpro69.kfaker.Faker
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class UserFactory(
  private val passwordEncoder: PasswordEncoder,
  private val userRepository: UserRepository,
) {
  private val faker = Faker()

  fun create(
    username:String = faker.random.randomString(8),
    email:String = faker.internet.email(),
    password:String = faker.random.randomString(8),
    role:Role = Role.USER,
    save:Boolean
  ): User {
    return User().also {
      it.password = passwordEncoder.encode(password)
      it.username = username
      it.createdAt = Instant.now()
      it.updatedAt = Instant.now()
      it.email = email
      it.unconfirmedEmail = email
      it.role = role

      if(save) userRepository.save(it)
    }
  }
}