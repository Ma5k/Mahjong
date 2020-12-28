package com.mask.mahjong.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class CommonController {
    @RequestMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response) {
        return "/index.html";
    }

    @RequestMapping("/start")
    public String start(HttpServletRequest request, HttpServletResponse response) {
        return "/start.html";
    }
}
