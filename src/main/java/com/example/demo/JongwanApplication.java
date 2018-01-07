package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;


/*
	To do list
	1. SecurityConfig에서 URI 막는것 vs @Secured로 막는것의 차이

	2. 쿠키 JSESSIONID 언제 어떻게 사용되는지
	3. BCryptPasswordEncoder()
	4. http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); 의 의미
    5. CustomAuthenticationProvider에서 파라미터로 넘어온 userID를통해 DB에서 사용자 정보를 조회하고 화면으로부터 넘어온 사용자 정보와 비교하는것 같은데
       DB에 값넣어서 실습해볼 것 (DB연결 안하고 하니 로그인이 안됨)
       -> 내가 생각하기론 AuthenticationProvider가 DB에 접근해서 값을 가져올때 커스텀했을경우 커스텀 UserDetailService를 이용하여 사용자 정보를 가져온다고 생각했는데
           이게 아닌거 같음

	Question
	1. FilterSecurityInterceptor를 사용하는 경우에 SecurityConfig로 앞단에서 해당 URL을 막을 수 있는지
	    -> SecurityConfig로 해당 URL 막지 못한다
	    -> 그럼 이럴경우에는 SecurityConfig에서 URL입력해봤자 소용없다는 의미


	Finished
	1. @Secured 어노테이션 먹는지, @PreAuthorize(표현식 기반의 어노테이션)
	2. redirect 와 forward차이 (RequestDispatcher dispatcher )
	3. AuthenticationEntryPoint 사용하는 경우 권한이없어 페이지에 denied되면 로그인페이지로 가나
	    -> login page로 가지않고 AuthenticationEntryPoint에서 설정한 url로 간다
	       설정하지않았으면 로그인 페이지로 이동
	4. FilterSecurityInterceptor를 사용하는 경우에 Voter에서 막았을경우 @Secured까지 들어오는가
	   -> 못들어온다
	5. UserDetails, UserDetailsService 언제 사용하는지
       -> 커스텀 로그인 처리할 때 사용
       -> AuthenticationProvider authenticate()에서 사용
       -> DB로부터 사용자 정보 가져올 때
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
