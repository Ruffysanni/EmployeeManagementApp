package com.ruffy.EmployeeManagementApp.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JWTService {
    // Secret key for signing the JWT
    private  static  final String SECRET_KEY = "6QP+6UyKPPn69L51WLy7sNGVLzUywFFWDuAVhlxwP22fKyZf8MSOvDzMvIer5S+a9l06gdN5kTZ+KmdDeVvYxA==";


    // Create a signing key from the base64 encoded secret
    private Key getSignKey(){
        byte[] bytesKey = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(bytesKey);
    }
    // Generate a token for the given username
    public String generateToken(String username){
        // Prepare claims for the token
        Map<String, Object> claims = new HashMap<>();
        // Build jwt token with claims, subject, issued time, expiration time and signing algorithm
        // Make token valid for 5 minutes
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 5))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Extract claim from the JWT token
    private Claims extractAllClaims(String token){
        // Parse and return all claims from the token
        return  Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
    }

    // Extract a specific Claim
    private <T> T extractClaim(String token, Function<Claims, T>claimsTFunction){
        final Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    // Extract username from the claim
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    // Extract Expiration date
    public Date extractExpirationDate(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    // Check if token is expired
    public boolean isTokenExpired(String token){
        return extractExpirationDate(token).before(new Date());
    }

    // Validate JWT token with the UserDetails
    public boolean validateToken(String token, UserDetails userDetails){
        // Extract username from the token and check if it matches the UserDetails username
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
