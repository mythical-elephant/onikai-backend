package com.onikai.backend.controller.authentication

import com.onikai.backend.validation.NotInappropriate
import com.onikai.backend.validation.NotReservedWord
import com.onikai.backend.validation.UniqueUsername
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import org.springframework.validation.annotation.Validated

@Validated
data class RegistrationRequestDTO (
  @field:Size(min = 3, max = 20, message = "Username must be between {min} and {max}")
  @field:Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Username contains invalid characters")
  @field:NotReservedWord
  @field:UniqueUsername
  @field:NotInappropriate
  val username:String,
  val password:String,
  @field:Email(regexp = "^(?=.{1,64}@)[A-Za-z0-9\\+_-]+(\\.[A-Za-z0-9\\+_-]+)*@[^-][A-Za-z0-9\\+-]+(\\.[A-Za-z0-9\\+-]+)*(\\.[A-Za-z]{2,})$", message = "'\${validatedValue}' is not a valid email address")
  val email:String,
)

