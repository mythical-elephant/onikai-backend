package com.onikai.backend.config

import com.onikai.backend.repository.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

@Configuration
class ApplicationConfig(
  val userRepository: UserRepository
) {
  @Bean
  fun userDetailsService(): UserDetailsService {
    return UserDetailsService { userEmail: String? ->
      if (userEmail == null) {
        throw UsernameNotFoundException("No such user")
      }
      userRepository
        .findUserByEmail(userEmail)
        .orElseThrow { UsernameNotFoundException("No such user") }
    }
  }
}