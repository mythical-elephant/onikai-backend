package com.onikai.backend.validation

import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass


@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [ReservedWordValidator::class])
annotation class NotReservedWord(
  val message: String = "Username is a reserved word.",
  val groups:Array<KClass<*>> = [],
  val payload:Array<KClass<out Payload>> = []
)

class ReservedWordValidator : ConstraintValidator<NotReservedWord, String> {
  private val reservedNames = listOf("api", "login","logout","update","exit","signup","sign_up","sign_in","signin","sign_out","signout","password","edit","confirmation","cancel","guest")


  override fun isValid(username: String?, context: ConstraintValidatorContext): Boolean {
    if (username == null) return false
    return username !in reservedNames
  }
}
