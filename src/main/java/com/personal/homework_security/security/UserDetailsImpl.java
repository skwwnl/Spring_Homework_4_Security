package com.personal.homework_security.security;

import com.personal.homework_security.entity.User;
import com.personal.homework_security.entity.UserRoleEnum;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayDeque;
import java.util.Collection;

public class UserDetailsImpl implements UserDetails {

    // 인증이 완료된 사용자 추가
    private final User user; // 인증이 완료된 User 객체
    private final String username; // 인증이 완료된 User의 ID

    public UserDetailsImpl(User user, String username){
        this.user = user;
        this.username = username;
    }

    // 인증이 완료된 User를 가져오는 Getter
    public User getUser(){
        return user;
    }

    @Override
    // 사용자의 권한 GrantedAuthority로 추상화 및 반환
    public Collection<? extends GrantedAuthority> getAuthorities(){
        UserRoleEnum role = user.getRole();
        String authority = role.getAuthority();

        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
        Collection<GrantedAuthority> authorities = new ArrayDeque<>();
        authorities.add(simpleGrantedAuthority);

        return authorities;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}