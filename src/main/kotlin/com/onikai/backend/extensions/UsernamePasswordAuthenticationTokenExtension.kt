package com.onikai.backend.extensions

import com.onikai.backend.model.enity.UserPrincipal
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority

/**
 * Wrapper around `UsernamePasswordAuthenticationToken` with getters for User properties
 */
class Auth(principal: Any?, credentials: Any?, authorities:Collection<GrantedAuthority?>?) : UsernamePasswordAuthenticationToken(principal, credentials, authorities) {
  val user:UserPrincipal
    get() = principal as UserPrincipal

  val userId:Long
    get() = user.id!!

  val username:String
    get() = user.username
}
