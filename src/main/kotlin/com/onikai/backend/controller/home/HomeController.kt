package com.onikai.backend.controller.home

import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HomeController {
  @GetMapping("/")
  fun index(model:ModelMap):String {
    model["content"] = "home"
    return "application"
  }
}