package com.insurance.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tc/input")
public class TcInputController {

  @GetMapping
  public String cm_security(Model model) {
    return "tc_input";
  }
}