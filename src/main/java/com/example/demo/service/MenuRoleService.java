package com.example.demo.service;

/**
 * Created by hongjong-wan on 2018. 1. 6..
 */
import com.example.demo.config.Permission;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MenuRoleService {

    public Permission findByMethodAndUrl(String httpMethod, String url) {
        // TODO Auto-generated method stub
        if(url.indexOf("/login") > -1){
            return new Permission("ROLE_ANONYMOUS");
        } else if(url.indexOf("/user") > -1) {
            log.info(" url : /user -> access!! ");
            return new Permission("R_ADMIN");
        } else if(url.indexOf("/") > -1){
            return new Permission("ROLE_ANONYMOUS");
        }else if(url.indexOf("favicon.ico")> -1) {
            return new Permission("ROLE_ANONYMOUS");
        }else {
            return new Permission("ROLE_ANONYMOUS");
        }
    }

}