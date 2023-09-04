package com.onikai.backend.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jsonMapper
import com.onikai.backend.model.enity.UserPrincipal
import com.onikai.backend.repository.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

@Configuration
class ApplicationConfig(
  val userRepository: UserRepository
) {
  @Bean
  fun userDetailsService(): UserDetailsService {
    return UserDetailsService { email: String? ->
      if (email == null) {
        throw UsernameNotFoundException("No such user")
      }

      UserPrincipal(userRepository
        .findUserByEmail(email)
        .orElseThrow { UsernameNotFoundException("No such user") }
      )
    }
  }

  @Bean
  fun jsonMapper(): ObjectMapper? {
    return JsonMapper
      .builder()
      .build()
      .registerModule(JavaTimeModule())
      .registerModule(KotlinModule())
  }
}