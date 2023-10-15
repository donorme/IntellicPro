package com.intellicpro.patent.auth.controller;

import com.intellicpro.patent.auth.dto.LoginDto;
import com.intellicpro.patent.auth.entity.Member;
import com.intellicpro.patent.auth.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping(value = "/login")
    public Member login(@RequestBody LoginDto loginDto){
    return memberService.login(loginDto);
    }


}
