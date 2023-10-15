package com.intellicpro.patent.security;

import com.intellicpro.patent.auth.entity.Member;
import com.intellicpro.patent.auth.type.Role;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

// spring security 라이브러이에 User가 있음
public class CustomUserDetails extends User {
    public Member member;
    public CustomUserDetails(Member member) {
        super(member.getCode(), "", authorities(member.getRole()));
        this.member = member;
    }
    private static Collection<? extends GrantedAuthority> authorities(Role role) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.name()));
        return authorities;
    }
}
