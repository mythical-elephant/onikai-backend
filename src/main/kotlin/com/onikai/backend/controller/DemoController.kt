package com.onikai.backend.controller

import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/demo-controller")
class DemoController {

  @GetMapping
  fun sayHello(auth: Authentication):ResponseEntity<String> {
    return ResponseEntity.ok("Hello from secured app")
  }
}