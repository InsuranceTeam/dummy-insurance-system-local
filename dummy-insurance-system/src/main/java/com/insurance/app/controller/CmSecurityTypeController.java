package com.insurance.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cm")
public class CmSecurityTypeController {

    //加入画面へ遷移するための保障選択画面
    @GetMapping("/security")
    public String cm_security(Model model) {
        model.addAttribute("task_name", "加入");
        model.addAttribute("task_link", "input");
        return "cm_security";
    }

    //契約照会画面へ遷移するための保障選択画面
    @GetMapping("s/security")
    public String cm_display(Model model) {
        model.addAttribute("task_name", "契約照会");
        model.addAttribute("task_link", "s/search");
        return "cm_security";
    }
}