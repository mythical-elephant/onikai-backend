package com.onikai.backend.service

import com.onikai.backend.controller.authentication.AuthenticationRequest
import com.onikai.backend.controller.authentication.AuthenticationResponse
import com.onikai.backend.controller.authentication.RegisterRequest
import com.onikai.backend.repository.UserRepository
import com.onikai.backend.model.enum.Role
import com.onikai.backend.model.enity.User
import jakarta.validation.Valid
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated
import org.springframework.web.server.ServerWebInputException
import java.time.Instant

@Service
@Validated
class AuthenticationService(
  val userRepository: UserRepository,
  val passwordEncoder: PasswordEncoder,
  val jwtService: JWTService,
  val authenticationManager: AuthenticationManager
) {
  fun register(@Valid request: RegisterRequest): AuthenticationResponse? {
    return register(request.username, request.password, request.password)
  }

  fun register(username:String, password:String, email:String): AuthenticationResponse? {
    if(password.length < 6) {
      throw ServerWebInputException("Password must be at least 6 characters")
    }

    val user = User().also {
      it._username = username
      it.email = email
      it.unconfirmedEmail = email
      it._password = passwordEncoder.encode(password)
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
