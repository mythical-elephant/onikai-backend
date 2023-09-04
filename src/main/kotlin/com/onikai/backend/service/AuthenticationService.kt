package com.onikai.backend.service

import com.onikai.backend.controller.authentication.AuthenticationRequestDTO
import com.onikai.backend.controller.authentication.AuthenticationResponseDTO
import com.onikai.backend.controller.authentication.RegistrationRequestDTO
import com.onikai.backend.repository.UserRepository
import com.onikai.backend.model.enum.Role
import com.onikai.backend.model.enity.User
import com.onikai.backend.model.enity.UserPrincipal
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
  fun register(@Valid request: RegistrationRequestDTO): AuthenticationResponseDTO? {
    if(request.password.length < 6) {
      throw ServerWebInputException("Password must be at least 6 characters")
    }

    val user = User().also {
      it.username = request.username
      it.email = request.email
      it.unconfirmedEmail = request.email
      it.password = passwordEncoder.encode(request.password)
      it.createdAt = Instant.now()
      it.updatedAt = Instant.now()
      it.role = Role.USER
    }
    userRepository.save(user)
    return AuthenticationResponseDTO(jwtService.generateToken(UserPrincipal(user)))
  }

  fun authenticate(request: AuthenticationRequestDTO): AuthenticationResponseDTO? {
    val authentication = authenticationManager
      .authenticate(
        UsernamePasswordAuthenticationToken(request.usernameOrEmail, request.password)
      )

    val user = when (request.isEmailAddress()) {
      true -> userRepository.findUserByEmail(request.usernameOrEmail)
      false -> userRepository.findUserByUsername(request.usernameOrEmail)
    }
      .orElseThrow { UsernameNotFoundException("No such user") }

    return AuthenticationResponseDTO(jwtService.generateToken(UserPrincipal(user)))
  }
}
