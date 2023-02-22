package com.personal.homework_security.config;

import com.personal.homework_security.jwt.JwtAuthFilter;
import com.personal.homework_security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
@EnableGlobalMethodSecurity(securedEnabled = true) // @Secured 어노테이션 활성화
public class WebSecurityConfig {

    // DI 주입
    private final JwtUtil jwtUtil;

    @Bean // Spring(IoC) Container에 Bean을 등록하도록 하는 메타데이터를 기입하는 Annotation
    // 외부라이브러리 사용을 위해 Bean으로 등록.
    public PasswordEncoder passwordEncoder() {
        // BCrpyt라는 해시 함수를 사용한 구현체. Salt를 넣는 작업까지 하므로 입력값이 같아도 다른 encoded 값을 return
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        // h2-console 사용 및 resources 접근 허용 설정
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toH2Console())
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();

        // 기본 설정인 Session 방식은 사용하지 않고 JWT 방식을 사용하기 위한 설정.
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // authorizeRequests() : 요청에 대한 권한 지정. Security 처리에 HttpServletRequest를 이용하는 의미.
        // antMatchers() : 특정 경로를 지정합니다. 뒤에 다른 메소드가 붙는다.
        // permitAll() : 어떤 사용자든지 접근할 수 있다.
        http.authorizeRequests().antMatchers("/api/**").permitAll()
//                .antMatchers("/api/search").permitAll()
//                .antMatchers("/api/shop").permitAll()
                .anyRequest().authenticated()
                // JWT 인증/인가를 사용하기 위한 설정.
                // UsernamePasswordAuthenticationFilter보다 먼저 실행 됨.
                .and().addFilterBefore(new JwtAuthFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);
//
//        http.formLogin().loginPage("/api/user/login-page").permitAll();

        http.exceptionHandling().accessDeniedPage("/api/user/forbidden");

        return http.build();
    }
}