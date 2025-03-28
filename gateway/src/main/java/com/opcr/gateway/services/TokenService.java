package com.opcr.gateway.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class TokenService {

    private static final String SECRET = "EF5C46D55EDBE887C561907A2FEC10E9F39F03E63364422CCF35A2746FB2A814";
    private static final long EXPIRATION_TIME = TimeUnit.MINUTES.toMillis(15);

    /**
     * Generate a JWT token for a user.
     *
     * @param username username of the User.
     * @return a JWT token.
     */
    public String generateTheToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusMillis(EXPIRATION_TIME)))
                .signWith(generateTheSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Generate a SecretKey from the String SECRET. Needed to sign the payload.
     *
     * @return a SecretKey.
     */
    private SecretKey generateTheSecretKey() {
        byte[] keyBytes = SECRET.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Check if the token is valid. Made with the key and not expired.
     *
     * @param token the token to check.
     * @return true if valid.
     */
    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(generateTheSecretKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    /**
     * Extract the username from the token.
     *
     * @param token the token.
     * @return the username from the token.
     */
    public String extractUsername(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(generateTheSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}
