package com.onikai.backend.validation

import com.onikai.backend.config.CurseWordConfig
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import kotlin.reflect.KClass


@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [NotInappropriateValidator::class])
annotation class NotInappropriate(
  val message: String = "Inappropriate value for '{propertyName}'",
  val groups:Array<KClass<*>> = [],
  val payload:Array<KClass<out Payload>> = []
)

@Component
class NotInappropriateValidator : ConstraintValidator<NotInappropriate, String> {
  @Autowired lateinit var curseWordConfig: CurseWordConfig

  override fun isValid(str: String?, context: ConstraintValidatorContext): Boolean {
    if (str == null) return false
    val s = str.lowercase()
    return when {
      curseWordConfig.simple.any { it == s } -> false
      curseWordConfig.regexes.any { it.matches(s) } -> false
      else -> true
    }
  }
}
