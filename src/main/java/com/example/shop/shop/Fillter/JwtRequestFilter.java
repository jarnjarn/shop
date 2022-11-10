package com.example.shop.shop.Fillter;

import com.example.shop.shop.Util.CodeUtil;
import com.example.shop.shop.exception.ApiException;
import com.example.shop.shop.exception.ErrorResponse;
import com.example.shop.shop.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper;

    @Value("${app.JWT_AUTH_HEADER}")
    private String JWT_AUTH_HEADER;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws  ServletException, IOException {
        var isContinue = checkAndAuthenticateRequest(request, response);
        if (isContinue) {
            filterChain.doFilter(request, response);
        }
    }


    // check and authenticate request
    private boolean checkAndAuthenticateRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (isAuthenticated()) {
            return true;
        }
        var authHeader = Optional.ofNullable(request.getHeader(JWT_AUTH_HEADER));
        System.out.println(authHeader);
        if (authHeader.isEmpty()) {
            return true;
        }
        var token = CodeUtil.extractTokenFromAuthHeader(authHeader.get())
                .orElseThrow(() -> new BadCredentialsException("Thiếu token"));

        try {
            UserDetails userDetails;
            try {
                userDetails = retrieveUserDetailsFromToken(token);
                checkUserDetailsValid(userDetails);

            } catch (UsernameNotFoundException e) {
                throw new BadCredentialsException("token không hợp lệ");
            }

            setAuthToken(userDetails);
            return true;
        } catch (ApiException ex) {
            var errorResponse = new ErrorResponse(ex.getCode(), ex.getMessage());
            responseUnauthorized(response, errorResponse);
            return false;
        }
    }
    public UserDetails retrieveUserDetailsFromToken(String token) {
        Claims claims;
        try {
            claims = jwtService.extractAllClaims(token);
        } catch (JwtException ex) {
            throw new BadCredentialsException("Mã thông báo không đáng tin cậy");
        }

        var issuedAt = claims.getIssuedAt();
        if (jwtService.isTokenExpired(issuedAt)) {
            throw new BadCredentialsException("token hết hạn");
        }

        var username = claims.getSubject();
        try {
            // Must query user to load the roles, locked,...

            return userDetailsService.loadUserByUsername(username);
        } catch (UsernameNotFoundException ex) {
            throw new BadCredentialsException("Không tìm thấy người dùng");
        }
    }


   // check user details valid
    public void checkUserDetailsValid(UserDetails userDetails) {
        if (!userDetails.isEnabled()) {
            throw new DisabledException("Tài khoản bị vô hiệu hóa");
        }
        if (!userDetails.isAccountNonExpired()) {
            throw new AccountExpiredException("Tài khoản đã hết hạn");
        }
        if (!userDetails.isCredentialsNonExpired()) {
            throw new CredentialsExpiredException("Thông tin đăng nhập đã hết hạn");
        }
        if (!userDetails.isAccountNonLocked()) {
            throw new LockedException("Account is locked");
        }
    }

    // Set authentication for current request
    private boolean isAuthenticated() {

        return Objects.nonNull(SecurityContextHolder.getContext().getAuthentication());
    }

    // Set authentication for current request
    private void setAuthToken(UserDetails userDetails) {
        // Token is verified, no password needed
        var authToken = new UsernamePasswordAuthenticationToken(userDetails,
                null,
                userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    // Response 401 Unauthorized
    private void responseUnauthorized(HttpServletResponse response, ErrorResponse errorResponse)
            throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(errorResponse.code().getStatus().value());
        response.getOutputStream().print(jacksonObjectMapper.writeValueAsString(errorResponse));
        // Still different response headers from default response
        // Transfer-Encoding: chunked and no Content-Length
    }

}