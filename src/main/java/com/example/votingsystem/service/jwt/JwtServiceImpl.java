package com.example.votingsystem.service.jwt;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;

import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

/**
 * @author samwel.wafula
 * Created on 09/01/2024
 * Time 11:55
 * Project VotingSystem
 */
@Service
@Slf4j
public class JwtServiceImpl implements JwtService {
    String SecretKey = "413F4428472B4B6250655368566D5970337336763979244226452948404D6351";

    @Override
    public String generateToken(UserDetails userDetails) {
        return generateActualToken(new HashMap<>(), userDetails);
    }

    public String generateActualToken(HashMap<String, Object> claims, UserDetails userDetails) {
        log.info(" jwt used username {}",userDetails.getUsername());
        return Jwts.builder().setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Key getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SecretKey));
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }


    public Date getExpirationDate(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build()
                .parseClaimsJws(token).getBody().getExpiration();
    }
}
