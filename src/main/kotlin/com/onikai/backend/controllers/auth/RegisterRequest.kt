package com.onikai.backend.controllers.auth

data class RegisterRequest (
  val username:String,
  val email:String,
  val password:String,
)
