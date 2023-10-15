package com.intellicpro.patent.security;

import com.intellicpro.patent.auth.entity.Member;
import com.intellicpro.patent.auth.repository.MemberRepository;
import com.intellicpro.patent.global.exception.CustomException;
import com.intellicpro.patent.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service // Repository로 접근해서 DB에 요청을 해서 Service??
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String code) throws UsernameNotFoundException {
        Member member =
            memberRepository
                .findByCode(code)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));

        return new CustomUserDetails(member);
    }
}
