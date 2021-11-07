package com.insurance.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cm/security")
public class CmSecurityTypeController {

  @GetMapping
  public String cm_security(Model model) {
    return "cm_security";
  }
}