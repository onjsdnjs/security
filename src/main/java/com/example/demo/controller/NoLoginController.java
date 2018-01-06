package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by hongjong-wan on 2018. 1. 6..
 */
@Controller
public class NoLoginController {

    @ResponseBody
    @RequestMapping("/no/msg")
    @ResponseStatus(value= HttpStatus.UNAUTHORIZED, reason="Unauthorized !!!")
    public void msg(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }

    @ResponseBody
    @RequestMapping("/no/page")
    public String page() {
        return "no page!!!";
    }
}
