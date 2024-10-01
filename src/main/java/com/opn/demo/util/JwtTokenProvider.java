package com.opn.demo.util;

import com.opn.demo.entity.Principal;
import io.jsonwebtoken.*;

import java.util.Date;

import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;


@Component
public class JwtTokenProvider {

  private final String jwtSecret="YourVeryLongSecretKeyStringThatHasAtLeast64CharactersToBeSafeForHS512Algorithm";


  private int jwtExpirationMs=3600000;

  public String generateToken(Authentication authentication) {
    Principal userPrincipal = (Principal) authentication.getPrincipal();
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

    byte[] keyBytes = jwtSecret.getBytes();

    if (keyBytes.length < 64) {
      throw new IllegalArgumentException("The jwtSecret must be at least 64 characters long");
    }

    return Jwts.builder()
          .setSubject(userPrincipal.getUsername())
          .setIssuedAt(new Date())
          .setExpiration(expiryDate)
          .signWith(Keys.hmacShaKeyFor(keyBytes))
          .compact();
  }

  public String getUserNameFromJwtToken(String token) {
    byte[] keyBytes = jwtSecret.getBytes();

    return Jwts.parser().
          setSigningKey(Keys.hmacShaKeyFor(keyBytes))
          .parseClaimsJws(token)
          .getBody()
          .getSubject();
  }

  public boolean validateJwtToken(String authToken) {

    byte[] keyBytes = jwtSecret.getBytes();

    try {
      Jwts.parser()
            .setSigningKey(Keys.hmacShaKeyFor(keyBytes))
            .parseClaimsJws(authToken);
      return true;
    } catch (MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException e) {
      return false;
    }
  }
}
