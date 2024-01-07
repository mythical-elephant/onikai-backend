package com.onikai.backend.controller

//import com.onikai.backend.extensions.user
import com.onikai.backend.extensions.Auth
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/api/v1/demo-controller")
class DemoController {

  @GetMapping
  fun sayHello(auth: Auth):ResponseEntity<String> {
//    auth.user.user.id
    return ResponseEntity.ok("Hello '${auth.username}' from secured app")
  }
}