package com.example.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;


/**
 * Created by hongjong-wan on 2018. 1. 6..
 */

// 인증을 요구하는 페이지에 인증이 되지 않은 경우(비로그인)에는
// AuthenticationEntryPoint 부분에서 AuthenticationException 을 발생
@Slf4j
@Component
public class SampleAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        log.info(" Don't access this url ");

        // 직접 URI 치고 들어오는경우 content-type이 없어 null일 수가 있다
        String contentType = request.getHeader("Content-Type");
        log.info(" contentType : {} ", contentType);
        if (contentType != null) {
            boolean isAjax = contentType.contains("application/json");

            // 호출 케이스별 리턴타입 결정
            if (isAjax) {
                // JSON

//                PrintWriter writer = response.getWriter();
//
//                String jsonTxt = "{\"code\":\"200\", \"msg\":\"success\"}";
//                writer.print(jsonTxt);
                response.sendRedirect("/no/msg");
            }
        } else {
            // ModelAndView
            response.sendRedirect("/no/page");
        }

        // 에러 페이지에 대한 확장자를 현재 호출한 확장자와 마추어준다.
//        String goErrorPage = convertorViewTypeErrorPage(request, errorPage);
//        if (redirect) {
//            response.sendRedirect(goErrorPage);
//        } else {
//            RequestDispatcher dispatcher = request.getRequestDispatcher(goErrorPage);
//            dispatcher.forward(request, response);
//        }

    }

}


//        log.info(" request.getRequestURI() : {} ", request.getRequestURI()); // 자원의 위치만, /mypage
//        log.info(" request.getRequestURL() : {} ", request.getRequestURL()); // 풀 경로, http://localhost:8080/mypage


//        Enumeration eHeader = request.getHeaderNames();
//        while (eHeader.hasMoreElements()) {
//            String hName = (String) eHeader.nextElement();
//            String hValue = request.getHeader(hName);
//            log.info(" {} : {} ", hName, hValue);
//        }