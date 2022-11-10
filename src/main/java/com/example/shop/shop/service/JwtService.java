package com.example.shop.shop.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final SignatureAlgorithm DEFAULT_SIGN_ALGORITHM = SignatureAlgorithm.HS256;
    private final Key signingKey = Keys.secretKeyFor(DEFAULT_SIGN_ALGORITHM);

    @Value("${app.JWT_ACCESS_TOKEN_TTL}")
    private long JWT_ACCESS_TOKEN_TTL;

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .signWith(signingKey, DEFAULT_SIGN_ALGORITHM)
                .compact();
    }

    public Claims extractAllClaims(String token) {
        System.out.println("token: " + token);
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenExpired(@Nullable Date issuedAt) {
        if (Objects.isNull(issuedAt)) {
            return true;
        }
        return issuedAt.getTime() + JWT_ACCESS_TOKEN_TTL < System.currentTimeMillis();
    }

}
