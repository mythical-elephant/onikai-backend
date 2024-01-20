package com.onikai.backend.extensions

import com.onikai.backend.model.enity.UserPrincipal
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken

val UsernamePasswordAuthenticationToken.user: UserPrincipal
  get() = this.principal as UserPrincipal