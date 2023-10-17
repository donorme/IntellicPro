package com.intellicpro.patent.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String TOKEN_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";
    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws IOException {

        try {
            String token = resolveTokenFromRequest(request);
            // 인증(Authentication) - 만약 토큰이 null이 아니거나, 인증된 토큰이라면 (맞나?), 권한을 주는 다음 작업 수행,
            // 아니면 예외처리
            if (token != null && tokenProvider.validateToken(token)) {
                Authentication auth = tokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(auth);

                log.info("'{}' -> {}", tokenProvider.getUsername(token), request.getRequestURI());
            }
            filterChain.doFilter(request, response);
        } catch (MalformedJwtException e) {
            (response).sendError(401, "변조된 토큰 입니다.");
        } catch (ExpiredJwtException e) {
            (response).sendError(401, "만료된 토큰 입니다.");
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
        }
    }

    // request에는 code와 meberId, role 등의 정보가 있음.
    //클라이언트로부터 온 요청의 토큰 정보와, 발급된 토큰 정보를 비교해서 동일하면 토큰을 리턴, 아니면 null 리턴

    private String resolveTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader(TOKEN_HEADER);
        if (token != null && token.startsWith(TOKEN_PREFIX)) {
            return token.substring(TOKEN_PREFIX.length());
        }

        return null;
    }
}
