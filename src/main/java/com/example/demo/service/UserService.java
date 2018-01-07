package com.example.demo.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collection;

/**
 * Created by hongjong-wan on 2018. 1. 7..
 */
public interface UserService extends UserDetailsService {
    Collection<GrantedAuthority> getAuthorities(String username);
}

