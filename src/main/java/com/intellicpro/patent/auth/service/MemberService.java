package com.intellicpro.patent.auth.service;

import com.intellicpro.patent.auth.dto.LoginDto;
import com.intellicpro.patent.auth.entity.Member;
import com.intellicpro.patent.auth.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
private final MemberRepository memberRepository;
    public Member login(LoginDto loginDto) {
        Member member = Member.builder()
            .code(loginDto.getCode())
            .name(loginDto.getName())
            .password(loginDto.getPassword())
            .build();

        return memberRepository.save(member);
    }
}
