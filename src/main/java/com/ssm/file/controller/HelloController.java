package com.ssm.file.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/** 脚手架自检页：确认 SSM 容器与 JSP 视图可用。 */
@Controller
public class HelloController {

    @GetMapping("/hello")
    public String hello(Model model) {
        model.addAttribute("message", "SSM 脚手架已启动，环境自检通过。");
        return "hello";
    }
}
