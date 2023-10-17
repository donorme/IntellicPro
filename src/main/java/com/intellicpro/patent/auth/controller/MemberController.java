package com.intellicpro.patent.auth.controller;

import com.intellicpro.patent.auth.dto.Login;
import com.intellicpro.patent.auth.dto.SignUp;
import com.intellicpro.patent.auth.entity.Member;
import com.intellicpro.patent.auth.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<SignUp.Response> signUp(@RequestBody @Valid SignUp.Request request) {

        Member member = memberService.signUp(request);

        return ResponseEntity.ok(SignUp.Response.from(member));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid Login request) {
        String token = memberService.login(request);

        return ResponseEntity.ok(token);
    }
}
