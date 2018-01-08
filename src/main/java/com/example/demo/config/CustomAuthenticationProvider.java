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
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    PasswordEncoder passwordEncoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        // 입력한 암호를 암호화 한다.
        String password = (String) authentication.getCredentials();


        log.info(" username : {}, password : {} ", username, password);


        User user = null;
        Collection<? extends GrantedAuthority> authorities = null;
        try {
            user = (User) userService.loadUserByUsername(username);

            String bcryptPassword = passwordEncoder.encode(user.getPassword());
            log.info(" password from DB : {} ", bcryptPassword);

            /*
                동일한 비밀번호를 입력했지만, 암호화를해서 DB에 저장된 값과 새로 암호화한 값이 다르기 때문에 비교가 불가
                이것을 match 함수가 해결해준다
                이용자가 로그인 폼에서 입력한 비밀번호와 DB로부터 가져온 암호화된 비밀번호를 비교한다
             */
            if ( !passwordEncoder.matches(password, bcryptPassword) ) {
                throw new BadCredentialsException( "암호가 일치하지 않습니다." );
            }


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
