package com.onikai.backend.config

import io.jsonwebtoken.io.Decoders
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import javax.naming.ConfigurationException

@Component
class ApplicationConstants {
  var logger: Logger = LoggerFactory.getLogger(ApplicationConstants::class.java)
  /**
   * 256bit secret key that is used to encode JWT secrets
   */
  @Value("\${onikai.jwt.secret}")
  var jwtSecretKeyString:String? = null
  /**
   * Secret key converted to bytes
   */
  val jwtSecretKey:ByteArray by lazy {
    if(jwtSecretKeyString == null) {
      logger.error("Secret Key cannot be null")
      throw ConfigurationException("Secret Key cannot be null")
    }
    Decoders.BASE64.decode(jwtSecretKeyString)
  }
}