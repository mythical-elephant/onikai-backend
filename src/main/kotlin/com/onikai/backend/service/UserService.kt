package com.onikai.backend.service

import com.onikai.backend.model.enity.User
import com.onikai.backend.repository.UserRepository
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull


@Service
class UserService(
  private val userRepository: UserRepository
) {
  fun find(id:Long): User? = userRepository.findById(id).getOrNull()
}