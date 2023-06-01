package com.ulascan.apigateway.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class JwtUtil {


    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";


    public void validateToken(final String token) {
        Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
    }

    public String extractRoleFromJwt(String jwtToken) {

        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(SECRET)
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
        return claims.get("role", String.class);
    }

    public String extractEmailFromJwt(String jwtToken) {

        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(SECRET)
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
        return claims.get("sub", String.class);
    }



    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
