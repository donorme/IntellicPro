package com.intellicpro.patent.auth.service;

import com.intellicpro.patent.auth.dto.Login;
import com.intellicpro.patent.auth.dto.SignUp.Request;
import com.intellicpro.patent.auth.entity.Member;
import com.intellicpro.patent.auth.repository.MemberRepository;
import com.intellicpro.patent.auth.type.Role;
import com.intellicpro.patent.global.exception.CustomException;
import com.intellicpro.patent.global.exception.ErrorCode;
import com.intellicpro.patent.security.TokenProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @Transactional
    public Member signUp(Request request) {

        if (memberRepository.existsByCode(request.getCode())) {
            throw new CustomException(ErrorCode.ALREADY_SIGNUP);
        }

        String password = passwordEncoder.encode(request.getPassword());

        return memberRepository.save(
            Member.builder()
                .code(request.getCode())
                .password(password)
                .name(request.getName())
                .gender(request.getGender())
                .phoneNumber(request.getPhoneNumber())
                .role(Role.ROLE_MEMBER)
                .build());
    }

    @Transactional
    public String login(Login request) {
        Member member =
            memberRepository
                .findByCode(request.getCode())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new CustomException(ErrorCode.FAILED_LOGIN);
        }

        return tokenProvider.generateToken(member.getCode(), member.getId(), member.getRole());
    }
}
