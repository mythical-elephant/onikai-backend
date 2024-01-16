package com.onikai.backend.service

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class RedisService(
  val redisTemplate: RedisTemplate<String, Any>,
  val mapper:ObjectMapper
) {
  /**
   * Assuming values are stored as JSON strings returns the value of `key` as type `T`
   */
  var logger: Logger = LoggerFactory.getLogger(RedisService::class.java)
  final inline fun <reified T> retrieveValue(key: String): T? {
    val stringValue = redisTemplate.opsForValue().get(key) as? String
    return stringValue?.let { mapper.readValue(it, T::class.java) }
  }

  final fun setValue(key: String, value:Any, duration:Duration = Duration.ofDays(1)) {
    redisTemplate.opsForValue().set(key, value, duration)
  }

  final fun hasValue(key: String):Boolean {
    return redisTemplate.opsForValue().get(key) != null
  }
}