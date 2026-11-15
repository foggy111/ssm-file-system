package com.ssm.file.controller;

import com.ssm.file.entity.FileInfo;
import com.ssm.file.entity.SysUser;
import com.ssm.file.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@Controller
public class FileController {

    private static final Logger log = LoggerFactory.getLogger(FileController.class);

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

    /**
     * 按 fileId 下载。磁盘路径只从数据库读取，绝不拼接用户输入的文件名。
     */
    @GetMapping("/files/download/{id}")
    public void download(@PathVariable("id") Integer id,
                         HttpSession session,
                         HttpServletResponse response) throws IOException {
        SysUser user = currentUser(session);
        FileInfo info = fileService.findOwned(id, user.getId());
        if (info == null) {
            writeFriendly(response, "无权访问，或文件不存在");
            return;
        }
        if (info.getFrozen() != null && info.getFrozen() == 1) {
            writeFriendly(response, "该文件已冻结，暂不可下载");
            return;
        }
        Path path = fileService.resolveDiskPath(info);
        if (!Files.exists(path) || !Files.isRegularFile(path)) {
            writeFriendly(response, "文件已删除或不存在");
            return;
        }
        String encoded = URLEncoder.encode(info.getOriginName(), StandardCharsets.UTF_8.name())
                .replace("+", "%20");
        String contentType = info.getContentType();
        if (contentType == null || contentType.trim().isEmpty()) {
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
        response.setContentType(contentType);
        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + encoded);
        response.setContentLengthLong(Files.size(path));
        try (InputStream in = Files.newInputStream(path);
             OutputStream out = response.getOutputStream()) {
            byte[] buf = new byte[8192];
            int n;
            while ((n = in.read(buf)) != -1) {
                out.write(buf, 0, n);
            }
            out.flush();
        }
        try {
            fileService.recordDownload(info.getId(), user.getId());
        } catch (Exception e) {
            log.warn("记录下载次数失败, fileId={}", info.getId());
        }
    }

    @PostMapping("/files/freeze/{id}")
    public String freeze(@PathVariable("id") Integer id,
                         HttpSession session,
                         RedirectAttributes ra) {
        try {
            fileService.toggleFrozen(id, currentUser(session).getId());
            ra.addFlashAttribute("success", "已更新冻结状态");
        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("error", "无权访问");
        }
        return "redirect:/files";
    }

    @PostMapping("/files/delete/{id}")
    public String delete(@PathVariable("id") Integer id,
                         HttpSession session,
                         RedirectAttributes ra) {
        try {
            fileService.logicalDelete(id, currentUser(session).getId());
            ra.addFlashAttribute("success", "已删除（逻辑删除，记录仍保留）");
        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("error", "无权访问");
        }
        return "redirect:/files";
    }

    @GetMapping("/error")
    public String errorPage() {
        return "error";
    }

    private SysUser currentUser(HttpSession session) {
        return (SysUser) session.getAttribute("loginUser");
    }

    private void writeFriendly(HttpServletResponse response, String message) throws IOException {
        response.reset();
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("text/html;charset=UTF-8");
        String escaped = message.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
        response.getWriter().write(
                "<!DOCTYPE html><html lang='zh-CN'><head><meta charset='UTF-8'><title>提示</title>"
                        + "<link rel='stylesheet' href='css/style.css'></head><body class='page'>"
                        + "<div class='card'><h2>提示</h2><p>" + escaped
                        + "</p><p><a href='javascript:history.back()'>返回</a></p></div></body></html>");
    }
}
