package com.onikai.backend.controller.authentication

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
  @field:Size(min = 3, max = 20)
  @field:Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9]*$")
  @field:NotReservedWord
  @field:UniqueUsername
  val username:String,
  val password:String,
  val email:String,
)

