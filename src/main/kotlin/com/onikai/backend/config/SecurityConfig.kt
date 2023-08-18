package com.onikai.backend.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig(
  val jwtAuthFilter: JWTAuthenticationFilter,
  val userDetailsService: UserDetailsService,
) {

  @Bean
  fun securityFilterChain(httpSecurity:HttpSecurity):SecurityFilterChain {
    httpSecurity
      .csrf { csrf -> csrf.disable() }
      .authorizeHttpRequests { auth ->
        auth
          // Allow everything here to go through
//          .requestMatchers("/api/v1/auth/**")
//          .authenticated()
          .anyRequest() // anything else authenticated
          .permitAll()
          // Everything else authenticate
      }
      // Stateless meaning all request will use JWT and not set a cookie
      .sessionManagement { session ->
        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      }
      .authenticationProvider(authenticationProvider())
      .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter::class.java)
    return httpSecurity.build()
  }


  @Bean
  fun authenticationProvider():AuthenticationProvider {
    val authProvider = DaoAuthenticationProvider()
    authProvider.setUserDetailsService(userDetailsService)
    authProvider.setPasswordEncoder(passwordEncoder())

    return authProvider
  }

  @Bean
  @Throws(Exception::class)
  fun authenticationManger(config: AuthenticationConfiguration): AuthenticationManager {
    return config.authenticationManager
  }

  @Bean
  fun passwordEncoder(): PasswordEncoder {
    return BCryptPasswordEncoder()
  }

}