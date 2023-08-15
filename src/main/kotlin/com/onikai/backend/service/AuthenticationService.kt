package com.onikai.backend.service

import com.onikai.backend.controller.authentication.AuthenticationRequest
import com.onikai.backend.controller.authentication.AuthenticationResponse
import com.onikai.backend.controller.authentication.RegisterRequest
import com.onikai.backend.repository.UserRepository
import com.onikai.backend.model.enum.Role
import com.onikai.backend.model.enity.User
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class AuthenticationService(
  val userRepository: UserRepository,
  val passwordEncoder: PasswordEncoder,
  val jwtService: JWTService,
  val authenticationManager: AuthenticationManager
) {
  fun register(request: RegisterRequest): AuthenticationResponse? {
    val user = User().also {
      it._username = request.username
      it.email = request.email
      it.unconfirmedEmail = request.email
      it._password = passwordEncoder.encode(request.password)
      it.createdAt = Instant.now()
      it.updatedAt = Instant.now()
      it.role = Role.USER
    }
    userRepository.save(user)
    return AuthenticationResponse(jwtService.generateToken(user))
  }

  fun authenticate(request: AuthenticationRequest): AuthenticationResponse? {
    val authentication = authenticationManager
      .authenticate(
        UsernamePasswordAuthenticationToken(request.email, request.password)
      )

    val user = userRepository
      .findUserByEmail(request.email)
      .orElseThrow { UsernameNotFoundException("No such user") }

    return AuthenticationResponse(jwtService.generateToken(user))
  }
}
