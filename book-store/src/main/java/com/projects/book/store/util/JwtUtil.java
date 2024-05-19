package com.projects.book.store.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import com.projects.book.store.model.User;

public class JwtUtil {


  private static final String SECRET_KEY = "your_hardcoded_secret_key"; 
  private static final long EXPIRATION_TIME = 86400000; 

  private static SecretKey getSigningKey(){
    return Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(SECRET_KEY));
  }

  public static String generateToken(String subject) {
    return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
  }

  public static String extractId(String token){
    return extractClaims(token, Claims::getSubject);
  }

  public static boolean isTokenExpired(String token) {
    final Date expiration = extractClaims(token, Claims::getExpiration);
    return expiration.before(new Date());
  }

  public static boolean isValid(String token, User user){
    String id = extractId(token);
    return id.equals(String.valueOf(user.getId())) && !isTokenExpired(token);
  }

  public static <T> T extractClaims(String token, Function<Claims,T> resolver){
    Claims claims = extractAllClaims(token);
    return resolver.apply(claims);
  }

  private static Claims extractAllClaims(String token) {
    return Jwts.parserBuilder()
               .setSigningKey(getSigningKey())
               .build()
               .parseClaimsJws(token)
               .getBody();
  }
}
