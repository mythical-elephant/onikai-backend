package com.onikai.backend.controller.authentication

import com.onikai.backend.service.AuthenticationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthenticationController(
  val authenticationService: AuthenticationService
) {
  @PostMapping("/register")
  fun register(
    @RequestBody request: RegistrationRequestDTO
  ): ResponseEntity<AuthenticationResponseDTO> {
    return ResponseEntity.ok(authenticationService.register(request))
  }

  @PostMapping("/authenticate")
  fun authenticate(
    @RequestBody request: AuthenticationRequestDTO
  ): ResponseEntity<AuthenticationResponseDTO> {
    return ResponseEntity.ok(authenticationService.authenticate(request))
  }
}