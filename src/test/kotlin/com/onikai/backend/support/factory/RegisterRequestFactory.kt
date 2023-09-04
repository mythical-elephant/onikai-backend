package com.onikai.backend.support.factory

import com.onikai.backend.controller.authentication.RegisterRequest
import io.github.serpro69.kfaker.Faker
import org.springframework.stereotype.Component

@Component
class RegisterRequestFactory {
  private val faker = Faker()
  fun create(
    username:String = faker.funnyName.name(),
    email:String = faker.internet.email(),
    password:String = faker.random.randomString(8)
  ):RegisterRequest {
    return RegisterRequest(username, password, email)
  }
}