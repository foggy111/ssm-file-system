package com.ssm.file.controller;

import com.ssm.file.entity.SysUser;
import com.ssm.file.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/doLogin")
    public String doLogin(@RequestParam String username,
                          @RequestParam String password,
                          HttpSession session,
                          Model model) {
        SysUser user = userService.login(username, password);
        if (user == null) {
            model.addAttribute("error", "账号或密码错误");
            model.addAttribute("username", username);
            return "login";
        }
        session.setAttribute("loginUser", user);
        session.setAttribute("username", user.getUsername());
        return "redirect:/files";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
