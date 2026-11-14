package com.ssm.file.controller;

import com.ssm.file.entity.SysUser;
import com.ssm.file.service.FileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/files")
    public String list(HttpSession session, Model model) {
        SysUser user = currentUser(session);
        model.addAttribute("files", fileService.listMine(user.getId()));
        model.addAttribute("user", user);
        return "list";
    }

    @GetMapping("/files/upload")
    public String uploadPage(HttpSession session, Model model) {
        model.addAttribute("user", currentUser(session));
        return "upload";
    }

    @PostMapping("/files/upload")
    public String upload(@RequestParam("files") MultipartFile[] files,
                         HttpSession session,
                         RedirectAttributes ra) {
        SysUser user = currentUser(session);
        String error = fileService.upload(files, user);
        if (error != null) {
            ra.addFlashAttribute("error", error);
            return "redirect:/files/upload";
        }
        ra.addFlashAttribute("success", "上传成功");
        return "redirect:/files";
    }

    @GetMapping("/error")
    public String errorPage() {
        return "error";
    }

    private SysUser currentUser(HttpSession session) {
        return (SysUser) session.getAttribute("loginUser");
    }
}
