package com.intellicpro.patent.security;

import com.intellicpro.patent.auth.type.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Component // @Component - spring이 bean으로 등록, 자동으로 인스턴스 생성, 종속성 포함
@RequiredArgsConstructor
public class TokenProvider {

    private static final String KEY_ROLES = "role";
    private static final String KEY_MEMBER_ID = "memberId";
    private static final long EXPIRED_TIME = 10000 * 60 * 60; // 10 hour
    private final UserDetailsServiceImpl userDetailsService;

    // 개인키 정보
    @Value("${spring.jwt.secret}")
    private String secretKey;

    // 페이로드 정보인 code, memberId, role을 파라미터로 받고 토큰 발급
    // jsonwebtoken 라이브러리 내장함수인 Claims 사용
    public String generateToken(String code, Long memberId, Role userType) {
        Claims claims = Jwts.claims().setSubject(code);
        claims.put(KEY_ROLES, userType);
        claims.put(KEY_MEMBER_ID, memberId);

        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + EXPIRED_TIME);

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(expiredDate)
            .signWith(SignatureAlgorithm.HS512, secretKey)
            .compact();
    }

    // 인증(Authentication)
    // 인증이 되었다면, 새로운 토큰을 발급하고 리턴하는 메서드
    // 트랜젝션으로??
    @Transactional
    public Authentication getAuthentication(String jwt) {

        UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(jwt));

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return paresClaims(token).getSubject();
    }

    public boolean validateToken(String token) {
        if (!StringUtils.hasText(token)) {
            return false;
        }
        Claims claims = paresClaims(token);
        return !claims.getExpiration().before(new Date());
    }

    private Claims paresClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
