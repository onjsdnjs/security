package com.example.demo.config;


import com.example.demo.service.MenuRoleService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hongjong-wan on 2018. 1. 6..
 */


/*
   일반적으로는 Spring Security는 application이 시작할 때, 각각의 URL에 대한 ROLE을 설정하게 됩니다
   Spring Security를 이용하면 Authentication Bean 이 생성된다. 이를 Controller 에서 주입 받아 접근 할 수 있다.
  */

@Configuration
@EnableWebSecurity // springSecurityFilterChain가 자동으로 포함되짐
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    MenuRoleService menuRoleService;

    @Autowired
    UserService userService;

    @Autowired
    CustomAuthenticationProvider customAuthenticationProvider;


    @Override
    public void configure(WebSecurity web) throws Exception {
        // WebSecurity가 HttpSecurity보다 우선순위가 높은 관계로 주로 리소스를 여기서 풀어줌
//        web.ignoring().antMatchers("/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/mypage").hasRole("HONG")
//                .antMatchers("/**").authenticated()
                .antMatchers("/**").permitAll()
                .and()
                // 인증이 되지 않았을 경우(비로그인)에는 AuthenticationEntryPoint 부분에서 AuthenticationException 을 발생 시키고,
                // 인증(로그인)은 되었으나 해당 요청에 대한 권한이 없을 경우에는 AccessDeniedHandler 부분에서 AccessDeniedException 이 발생된다.
//                .exceptionHandling().authenticationEntryPoint(sampleAuthenticationEntryPoint())
//                .and()
                .addFilterAfter(filterSecurityInterceptor(), FilterSecurityInterceptor.class);


        http
                .formLogin()
                // 로그인 페이지 : url 매핑을 하지 않으면 기본 제공되는 로그인 페이지가 뜬다.
//                .loginPage("/login")
                .loginProcessingUrl("/auth/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/mypage") // 성공했을때 리다이렉트 url
                .and()
                .logout()
                .logoutUrl("/auth/logout");

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);


    }

    @Bean
    public FilterSecurityInterceptor filterSecurityInterceptor() throws Exception {
        FilterSecurityInterceptor filterSecurityInterceptor = new FilterSecurityInterceptor();
        filterSecurityInterceptor.setSecurityMetadataSource(securityMetadataSource()); // URL 권한
        filterSecurityInterceptor.setAuthenticationManager(authenticationManager()); // 사용자 권한
        filterSecurityInterceptor.setAccessDecisionManager(affirmativeBased()); //판단
        return filterSecurityInterceptor;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationEntryPoint sampleAuthenticationEntryPoint() {
        SampleAuthenticationEntryPoint sampleAuthenticationEntryPoint = new SampleAuthenticationEntryPoint();
        return sampleAuthenticationEntryPoint;
    }

    /*
      인증 객체를 만드는 클래스 : AuthenticationManagerBuilder

      인증처리 과정
       1) 접속자가 계정과 암호를 입력한다.
       2) 접속자가 입력한 계정을 이용하여 데이터베이서에서 사용자 정보를 조회한 결과를 UserDetails 라는 곳에 담는다.
       3) DB로부터 이용자 정보와 화면에서 입력한 정보를 비교하여 일치 한다면 사용자 정보를 사용할 수 있게 허가 한다.
       이 일을 담당하는 것이 AuthenticationProvider 가 하는 역활이다.
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
        auth.authenticationProvider(customAuthenticationProvider);
    }


    /*
     여러개의 Voter 객체들이 내린 결론들을 AbstractAccessDecisionManager 클래스를 상속받은 클래스 객체가
     모아놓은 후 이 객체가 자신만의 기준으로 종합적으로 판단해서 결정을 한다
     AffrirmativeBased 클래스는 등록된 Voter 클래스 객체 중 단 하나라도 접근 허가(ACCESS_GRANTED)로
     결론을 내면 최종적으로 접근 허가 한다고 판단한다

      여러 Voter를 추가하는 것이 가능. List로 index가 작은 Voter가 먼저 투표.
      투표 포기(ACCESS_ABSTAIN)가 되는 경우, 다음 Voter에게 넘어감.
     */
    @Bean
    public AffirmativeBased affirmativeBased() {
        FmsAccessDecisionVoter voter = new FmsAccessDecisionVoter();
        // In Spring 4, the constructor adds a type:
        List<AccessDecisionVoter<? extends Object>> voters = new ArrayList<>();
        voters.add(voter);
        return new AffirmativeBased(voters);
    }

    @Bean
    public FilterInvocationSecurityMetadataSource securityMetadataSource() {
        return new FmsFilterInvocationSecurityMetadataSource(menuRoleService);
    }


}

