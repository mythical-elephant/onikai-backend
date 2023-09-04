package com.onikai.backend.controller.authentication

import com.onikai.backend.validation.NotInappropriate
import com.onikai.backend.validation.NotReservedWord
import com.onikai.backend.validation.UniqueUsername
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import org.springframework.validation.annotation.Validated

@Validated
data class RegisterRequest (
  @field:Size(min = 3, max = 20, message = "Username must be between {min} and {max}")
  @field:Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Username contains invalid characters")
  @field:NotReservedWord
  @field:UniqueUsername
  @field:NotInappropriate
  val username:String,
  val password:String,
  val email:String,
)

