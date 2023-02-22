package com.personal.homework_security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal.homework_security.dto.SecurityExceptionDto;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j // Simple Logging Facade for Java
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter { // Filter를 상속 받아 만든다
    // 다른 인터페이스를 상속 받아 사용해도 된다.

    private final JwtUtil jwtUtil;

    @Override
    // FilterChain을 통해 다음 Filter로 이동하고
    // request Header 부분에 토큰을 가져오고 authorization 키 값으로 Bearer 붙어서
    // 그 토큰을 Jwtutil에 있는 resolveToken을 사용해서 가져옵니다.
    // 다음 토큰이 있는지 없는지 확인합니다.
    // 이제는 토큰이 request Header에 있냐 없냐를 확인.
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = jwtUtil.resolveToken(request);

        // User를 로그인, 회원가입 과정은 인증이 필요가 없어서 들어가는 URI 경우는 토큰이 Header에 들어있지 않아
        // 이렇게 분기 처리를 하지 않으면 토큰을 검증하는 부분에서 Exception이 발생.
        // 인증, 토큰이 없으면 다음 필터로 이동.
        if(token != null){
            if(!jwtUtil.validateToken(token)){
                // Handler Method
                // 아래에 존재 ( 이상이 있을 경우 )
                jwtExceptionHandler(response, "Token Error", HttpStatus.UNAUTHORIZED.value());
                return;
            } // 토큰에 이상이 없으면 과정 진행
            // 토큰에서 만들었던 User의 정보를 가져옵니다, Claims 객체에
            Claims info = jwtUtil.getUserInfoFromToken(token);
            // 아래 set.. 함수에 getSubject( = Username )을 보냄
            setAuthentication(info.getSubject());
        }
        // 다음 필터로 이동하는...
        // setAuthen...함수의 인증이 된 내용을 Controller까지 요청이 넘어간다.
        filterChain.doFilter(request, response);
    }

    // ======================================================================
    // 메소드
    // SecurityContextHolder에 넣는 로직이 수행
    public void setAuthentication(String username){
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = jwtUtil.createAuthentication(username);
        context.setAuthentication(authentication);

        // Ser...ContextHolder 안, SecurityContext에 인증 객체가 들어있다
        SecurityContextHolder.setContext(context);
    }

    // 메소드
    // 토큰에 대한 오류가 발생 했을 때, Client에게 커스터마이징해서 결과 값 = Exception 처리 값을 알려준다. 그런 메소드
    public void jwtExceptionHandler(HttpServletResponse response, String msg, int statusCode){
        response.setStatus(statusCode);
        response.setContentType("application/json");
        try {// SecurityExceptionDto로 객체를 만들고 ObjectMapper를 통해서 변환을 한 다음에 Client로 반환
            String json = new ObjectMapper().writeValueAsString(new SecurityExceptionDto(statusCode, msg));
            response.getWriter().write(json);
        } catch (Exception e){
            log.error(e.getMessage());
        }
    }
}