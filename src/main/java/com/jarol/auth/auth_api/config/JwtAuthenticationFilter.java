package com.jarol.auth.auth_api.config;

import com.jarol.auth.auth_api.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import io.jsonwebtoken.security.SignatureException;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = authHeader.substring(7);

        if (accessToken.isBlank()) {
            unauthorized(response);
            return;
        }

        try {
            Claims claims = jwtService.parseAndValidateToken(accessToken);

            UUID userId = UUID.fromString(claims.getSubject());
            UUID sessionId = UUID.fromString(claims.get("sessionId", String.class));
            String username = claims.get("username", String.class);
            List<String> roles = claims.get("roles", List.class);


            List<GrantedAuthority> authorities = roles.stream().map(SimpleGrantedAuthority::new).collect(java.util.stream.Collectors.toList());

            CustomUserDetails userDetails = new CustomUserDetails(
                    userId,
                    sessionId,
                    username,
                    authorities
            );

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    authorities
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (ExpiredJwtException ex) {

            logger.warn("Expired JWT token: " + ex.getMessage());

            unauthorized(response);
            return;

        } catch (SignatureException ex) {

            logger.warn("Invalid JWT signature: " + ex.getMessage());

            unauthorized(response);
            return;

        } catch (MalformedJwtException ex) {

            logger.warn("Malformed JWT token: " + ex.getMessage());

            unauthorized(response);
            return;

        } catch (UnsupportedJwtException ex) {

            logger.warn("Unsupported JWT token: " + ex.getMessage());

            unauthorized(response);
            return;

        } catch (IllegalArgumentException ex) {

            logger.warn("JWT token compact of handler are invalid: " + ex.getMessage());

            unauthorized(response);
            return;

        } catch (Exception ex) {

            logger.error("JWT authentication error: ", ex);

            unauthorized(response);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void unauthorized(HttpServletResponse response) {
        if (response.isCommitted()) {
            return;
        }

        SecurityContextHolder.clearContext();
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {

            response.getWriter()
                    .write("{\"error\":\"Unauthorized\"}");

        } catch (IOException e) {

            logger.error("Error writing unauthorized response", e);
        }
    }
}
