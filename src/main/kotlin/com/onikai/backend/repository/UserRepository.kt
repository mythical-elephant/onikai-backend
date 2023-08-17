package com.onikai.backend.repository

import com.onikai.backend.model.enity.User
import org.springframework.data.repository.CrudRepository
import java.util.Optional

interface UserRepository : CrudRepository<User, Long> {
  fun findUserByEmail(email:String):Optional<User>
}