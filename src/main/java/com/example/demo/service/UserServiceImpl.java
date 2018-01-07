package com.example.demo.service;

import com.example.demo.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by hongjong-wan on 2018. 1. 7..
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info(" user name : {} ", username);

        User user = new User();
        user.setUsername(username);
        user.setPassword("test");
        user.setAuthorities(getAuthorities(username));

        return user;
    }

    public Collection<GrantedAuthority> getAuthorities(String username) {
        List<String> string_authorities = new ArrayList<>(Arrays.asList("ROLE_HONG"));
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String authority : string_authorities) {
            authorities.add(new SimpleGrantedAuthority(authority));
        }
        return authorities;
    }
}

