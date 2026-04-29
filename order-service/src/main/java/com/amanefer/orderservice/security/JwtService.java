package com.amanefer.orderservice.security;

import com.amanefer.orderservice.config.props.SecurityProperties;
import com.amanefer.orderservice.model.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final SecurityProperties securityProperties;

    public String generateAccessToken(User user) {
        return generateToken(
                user,
                securityProperties.access().secret(),
                securityProperties.access().expiration()
        );
    }

    public String generateRefreshToken(User user) {
        return generateToken(
                user,
                securityProperties.refresh().secret(),
                securityProperties.refresh().expiration()
        );
    }

    public Claims extractClaims(String token, boolean isRefresh) {
        String secret = isRefresh
                ? securityProperties.refresh().secret()
                : securityProperties.access().secret();

        return Jwts.parser()
                .verifyWith(getSigningKey(secret))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isTokenValid(Claims claims, UserDetails userDetails) {
        final String username = claims.getSubject();
        boolean isTokenExpired = claims.getExpiration().before(new Date());

        return username.equals(userDetails.getUsername()) && !isTokenExpired;
    }


    private String generateToken(User user, String secret, long expirationTime) {
        return Jwts.builder()
                .subject(user.getUsername())
                .claim("userId", user.getId())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSigningKey(secret))
                .compact();
    }

    private SecretKey getSigningKey(String secret) {
        byte[] decoded = Decoders.BASE64.decode(secret);

        return Keys.hmacShaKeyFor(decoded);
    }
}
