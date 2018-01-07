package com.example.demo.config;

import com.example.demo.service.MenuRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;

import org.springframework.security.access.SecurityConfig;


/**
 * Created by hongjong-wan on 2018. 1. 6..
 * DB 기반의 인증 관리 시스템.
 * URL별 권한 설정
 */
@Slf4j
public class FmsFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private MenuRoleService menuRoleService;
    private FmsUrlParser parser;
    private Map<String, Permission> permissions;


    public FmsFilterInvocationSecurityMetadataSource(MenuRoleService menuRoleService) {
        this.menuRoleService = menuRoleService;
        parser = new FmsUrlParser();
        permissions = new Hashtable<>();
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        HttpServletRequest request = ((FilterInvocation) object).getRequest();
        String httpMethod = request.getMethod().toUpperCase();
        String url = parser.parse(request.getRequestURI());
        String key = String.format("%s %s", httpMethod, url);
        log.info(" key : {} ", key);


        Permission permission;
        log.info(" permissions.containsKey(key) : {} ", permissions.containsKey(key));
        if (permissions.containsKey(key)) {
            permission = permissions.get(key);
        } else {
            permission = menuRoleService.findByMethodAndUrl(httpMethod, url);
            if (permission != null) {
                permissions.put(key, permission);
            }
        }

        String[] roles;
        if (permission == null) {
            roles = new String[]{"ROLE_ADMIN"};
        } else {
            roles = new String[]{"ROLE_ADMIN", permission.getName()};
        }
        return SecurityConfig.createList(roles);

    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }


    // 무슨 의미길래? 코드를 넣어줘야지만 되는건가
    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);

    }


