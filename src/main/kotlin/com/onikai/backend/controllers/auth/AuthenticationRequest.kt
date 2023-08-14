package com.onikai.backend.controllers.auth

data class AuthenticationRequest(
  val email:String,
  val password:String,
)
