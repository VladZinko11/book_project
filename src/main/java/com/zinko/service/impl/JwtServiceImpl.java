package com.zinko.service.impl;

import com.zinko.data.model.User;
import com.zinko.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.token.signing.key}")
    private String jwtSigningKey;
    @Value("${jwt.token.expiration.time}")
    private Long expiration;

    @Override
    public String extractUserName(String jwt) {
        return extractClaim(jwt, Claims::getSubject);
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        if (userDetails instanceof User user) {
            claims.put("id", user.getId());
            claims.put("email", user.getEmail());
            claims.put("role", user.getRole());
        }
        return generateToken(claims, userDetails);
    }

    @Override
    public boolean isTokenValid(String jwt, UserDetails userDetails) {
        final String userName = extractUserName(jwt);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(jwt));
    }

    private boolean isTokenExpired(String jwt) {
        return extractClaim(jwt, Claims::getExpiration).before(new Date());
    }

    private String generateToken(Map<String, Object> claims, UserDetails userDetails) {
        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSigningKey)), Jwts.SIG.HS256).compact();
    }

    private <T> T extractClaim(String jwt, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(jwt);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String jwt) {
        return Jwts.parser().verifyWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSigningKey))).build().parseSignedClaims(jwt).getPayload();
    }
}
