package com.onikai.backend.controller.authentication

data class AuthenticationRequestDTO(
  val usernameOrEmail:String,
  val password:String,
) {
  fun isEmailAddress():Boolean = usernameOrEmail.contains('@')
}
