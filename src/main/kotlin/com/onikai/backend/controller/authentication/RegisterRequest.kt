package com.onikai.backend.controller.authentication

data class RegisterRequest (
  val username:String,
  val email:String,
  val password:String,
)
