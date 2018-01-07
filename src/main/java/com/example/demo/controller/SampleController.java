package com.example.demo.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by hongjong-wan on 2018. 1. 6..
 */
@Controller
public class SampleController {
    @ResponseBody
    @RequestMapping("/")
    public String sample() {
        return "hello world";
    }


    @Secured("ROLE_HONG")
    @ResponseBody
    @RequestMapping("/mypage")
    public String mypage() {
        return "This is mypage !!";
    }


    @ResponseBody
    @RequestMapping("/admin/page")
    public String admin() { return "Admin page !!";}


    @Secured("ROLE_ANONYMOUS")
    @ResponseBody
    @RequestMapping("/user")
    public String user() { return "User page !!";}
}