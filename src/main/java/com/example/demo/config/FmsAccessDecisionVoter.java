package com.example.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;

import java.util.Collection;

/**
 * Created by hongjong-wan on 2018. 1. 6..
 * AccessVote는 FilterInvocationSecurityMetadataSource에서 넘겨준 ROLE과
 * 사용자의 ROLE간의 비교를 해주게 됩니다
 * 사용자 ROLE : Authentication
 * URL별 ROLE : Collection<ConfigAttribute>
 */
@Slf4j
public class FmsAccessDecisionVoter implements AccessDecisionVoter<FilterInvocation> {

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return attribute instanceof SecurityConfig;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz != null && clazz.isAssignableFrom(FilterInvocation.class);
    }


    /*
         Authentication authentication : 사용자 권한정보
         Collection<ConfigAttribute> attributes : URL에 접근할 수 있는 권한목록

      */
    @Override
    public int vote(Authentication authentication, FilterInvocation object, Collection<ConfigAttribute> attributes) {
        SecurityConfig securityConfig = null;
        boolean containAuthority = false;
        for (final ConfigAttribute configAttribute : attributes) {
            if (configAttribute instanceof SecurityConfig) {
                securityConfig = (SecurityConfig) configAttribute;
                for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
                    log.info(" Authentication : {} ", grantedAuthority.getAuthority());
                    log.info(" Collection<ConfigAttribute> : {} ", securityConfig.getAttribute());
                    containAuthority = securityConfig.getAttribute().equals(grantedAuthority.getAuthority());
                    if (containAuthority) {
                        break;
                    }
                }
                if (containAuthority) {
                    break;
                }
            }
        }
        log.info(" ACCESS? : {} ", containAuthority ? ACCESS_GRANTED : ACCESS_DENIED);
        return containAuthority ? ACCESS_GRANTED : ACCESS_DENIED;
    }


}


