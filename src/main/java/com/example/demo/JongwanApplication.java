package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;


/*
	To do list
	1. FilterSecurityInterceptor를 사용하는 경우에 SecurityConfig로 앞단에서 해당 URL을 막을 수 있는지
	2. FilterSecurityInterceptor를 사용하는 경우에 Voter에서 막았을경우 @Secured까지 들어오는가
	3. SecurityConfig에서 URI 막는것 vs @Secured로 막는것의 차이
	3. UserDetails, UserDetailsService 언제 사용하는지
	4. 쿠키 JSESSIONID 언제 어떻게 사용되는지
	5. BCryptPasswordEncoder()



	Finished
	1. @Secured 어노테이션 먹는지, @PreAuthorize(표현식 기반의 어노테이션)
	2. redirect 와 forward차이 (RequestDispatcher dispatcher )

 */
/*
프로젝트
1. 로그인하지 않은 사용자는 ROLE_ANONYMOUS이므로 권한을 통해 차단

   -> FilterSecurityInterceptor에서 URL 별로 접근을 허용할 것인지 아닌지 판단하고 있다

2.
*/
@SpringBootApplication
public class JongwanApplication {

	public static void main(String[] args) {
		SpringApplication.run(JongwanApplication.class, args);
	}
}
