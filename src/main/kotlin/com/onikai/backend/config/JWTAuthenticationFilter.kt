package com.onikai.backend.config

import com.onikai.backend.service.JWTService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JWTAuthenticationFilter(
  val jwtService: JWTService,
  val userDetailsService: UserDetailsService
) : OncePerRequestFilter() {
  /**
   * Same contract as for `doFilter`, but guaranteed to be
   * just invoked once per request within a single request thread.
   * See [.shouldNotFilterAsyncDispatch] for details.
   *
   * Provides HttpServletRequest and HttpServletResponse arguments instead of the
   * default ServletRequest and ServletResponse ones.
   */
  override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
    val authHeader: String? = request.getHeader("authorization")
    if(authHeader == null
      || !authHeader.startsWith("Bearer ")
      || SecurityContextHolder.getContext().authentication != null
      ) {
      filterChain.doFilter(request, response)
      return
    }

    val jwtToken = authHeader.substring("Bearer ".length)
    val usernameOrEmail = jwtService.extractSubject(jwtToken)

    // If user email was extracted, and user is not authenticated
    if(usernameOrEmail != null) {
      val userDetails = userDetailsService.loadUserByUsername(usernameOrEmail)
      // If the user and the token are valid
      if(jwtService.tokenIsValid(jwtToken, userDetails)) {
        val authToken = UsernamePasswordAuthenticationToken(
          userDetails,
          null,
          userDetails.authorities
        )
        authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
        SecurityContextHolder.getContext().authentication = authToken
      }
    }

    filterChain.doFilter(request, response)
  }
}