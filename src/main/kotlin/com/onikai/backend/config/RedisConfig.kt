package com.onikai.backend.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer


@Configuration
class RedisConfig {
  @Bean
  fun connectionFactory(): RedisConnectionFactory {
    return LettuceConnectionFactory()
  }

  @Bean
  fun redisTemplate(mapper: ObjectMapper, connectionFactory: RedisConnectionFactory?): RedisTemplate<String, Any> {
    val template = RedisTemplate<String, Any>()
    template.connectionFactory = connectionFactory
    template.valueSerializer = GenericJackson2JsonRedisSerializer(mapper)
    return template
  }
}