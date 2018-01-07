package com.example.demo.config;

import com.example.demo.domain.User;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Created by hongjong-wan on 2018. 1. 7..
 */
@Component
@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        log.info(" username : {}, password : {} ", username, password);


        User user = null;
        Collection<? extends GrantedAuthority> authorities = null;
        try {
            user = (User) userService.loadUserByUsername(username);

            // 이용자가 로그인 폼에서 입력한 비밀번호와 DB로부터 가져온 암호화된 비밀번호를 비교한다
            if (!user.getPassword().equals(password))
                throw new BadCredentialsException("비밀번호 불일치");

            log.info(" password matching !!");

            authorities = user.getAuthorities();
            log.info(" user authorities : {} ", authorities.toString());
        } catch (UsernameNotFoundException e) {
            e.printStackTrace();
            throw new UsernameNotFoundException(e.getMessage());
        } catch (BadCredentialsException e) {
            e.printStackTrace();
            throw new BadCredentialsException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

        return new UsernamePasswordAuthenticationToken(username, password, authorities);
    }

    // 무슨 의미
    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
