package com.onikai.backend.services

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.security.Key
import java.time.Instant
import java.util.*

@Service
class JWTService {
  companion object {
    val SECRET_KEY =
      "37dbe639e868664d6f4f8b2068a3c47e8e903e6c89d178e9433218e78410b34878cb1c0d51208fda091772142d11c103fd42093f4089899ceec8ef4d0f55944b"
    val SECRET_KEY_BYTES: ByteArray by lazy { Decoders.BASE64.decode(SECRET_KEY) }
  }

  /**
   * Generate a token for the user and inject extra information via a map
   */
  fun generateToken(userDetails: UserDetails, extraClaims: Map<String, Any>? = emptyMap<String, Any>()): String {
    return Jwts
      .builder()
      .setClaims(extraClaims)
      .setSubject(userDetails.username)
      .setIssuedAt(Date(System.currentTimeMillis()))
      .setExpiration(Date(System.currentTimeMillis() * 1000 + 60 + 24))
      .signWith(getSigningKey(), SignatureAlgorithm.HS256)
      .compact()
  }

  fun tokenIsValid(token:String, userDetails: UserDetails):Boolean {
    val email = extractSubject(token) ?: return false
    return email == userDetails.username && !tokenIsExpired(token)
  }

  fun tokenIsExpired(token:String):Boolean {
    return extractExpiration(token)?.before(Date()) ?: false
  }

  /**
   * Extracts the subject, which in our case is the user's email, which we will use to authenticate them
   */
  fun extractSubject(token: String): String? {
    return extractClaim(token) { it.subject }
  }

  fun extractExpiration(token:String):Date? {
    return extractClaim(token, Claims::getExpiration)
  }

  fun <T> extractClaim(token: String, claimsResolver: (claims: Claims) -> T): T? {
    val claims = extractAllClaims(token) ?: return null
    return claimsResolver(claims)
  }

  /**
   * Extract the claims from the provided token.
   * Returns `null` if token cannot be parsed
   */
  fun extractAllClaims(token: String): Claims? {
    return try {
      Jwts
        .parserBuilder()
        .setSigningKey(getSigningKey())
        .build()
        .parseClaimsJws(token)
        .body
    } catch (e: Exception) {
      null
    }
  }

  /**
   * Signing key used
   */
  private fun getSigningKey(): Key {
    return Keys.hmacShaKeyFor(SECRET_KEY_BYTES)
  }
}