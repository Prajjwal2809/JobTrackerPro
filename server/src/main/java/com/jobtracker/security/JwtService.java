package com.jobtracker.security;


import java.security.Key;
import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;


@Service
public class JwtService {

    private final Key key;
    private final long expirationMinutes;

    public JwtService(@Value("${app.jwt.secret}") String base64Secret, 
    @Value("${app.jwt.expirationMinutes}") long expirationMinutes
)  {
        this.key = Keys.hmacShaKeyFor(java.util.Base64.getDecoder().decode(base64Secret));
        this.expirationMinutes = expirationMinutes;
    }

    public String generateToken(String subjectEmail) {
        OffsetDateTime now = OffsetDateTime.now();
        OffsetDateTime expiry = now.plusMinutes(expirationMinutes);

        return io.jsonwebtoken.Jwts.builder()
                .setSubject(subjectEmail)
                .setIssuedAt(java.util.Date.from(now.toInstant()))
                .setExpiration(java.util.Date.from(expiry.toInstant()))
                .signWith(key)
                .compact();
    }

    public String parseSubject(String token) {
        return parseClaims(token).getSubject();
    }

    public boolean isValid(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    private Claims parseClaims(String token) {
        return io.jsonwebtoken.Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    
}
