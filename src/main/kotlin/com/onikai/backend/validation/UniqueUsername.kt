package com.onikai.backend.validation

import com.onikai.backend.repository.UserRepository
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import kotlin.reflect.KClass


@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [UniqueUsernameValidator::class])
annotation class UniqueUsername(
  val message: String = "Username is a already taken",
  val groups:Array<KClass<*>> = [],
  val payload:Array<KClass<out Payload>> = []
)

@Component
class UniqueUsernameValidator : ConstraintValidator<UniqueUsername, String> {
  @Autowired
  lateinit var userRepository:UserRepository
  override fun isValid(username: String?, context: ConstraintValidatorContext): Boolean {
    if (username == null) return false
    return !userRepository.existsByUsername(username)
  }
}
