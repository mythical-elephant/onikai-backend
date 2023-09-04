package com.onikai.backend.controller.authentication

import com.fasterxml.jackson.annotation.JsonIgnore

data class AuthenticationRequestDTO(
  val usernameOrEmail:String,
  val password:String,
) {
  @JsonIgnore
  fun isEmailAddress():Boolean = usernameOrEmail.contains('@')
}
