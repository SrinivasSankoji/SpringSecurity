package com.chary.bhaumik.jwt.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JWTUtil 
{
	@Value("${security.jwt.token.secret-key}")
	private String secretkey;
	
	@Value("${security.jwt.token.expire-length}")
	private long validityInMilliseconds;
	
    public String extractSubject(String token) 
    {
        return extractClaim(token, Claims::getSubject);
    }
    
    public boolean validateToken(String token)
   	{
   		return !isTokenExpired(token);
   	}
    
    public String extractUsername(String token) 
    {
    	Claims claims = extractAllClaims(token);
        return (String) claims.get("userName");
    }
    
    public boolean isTokenExpired(String token) 
	{
		return extractExpiration(token).before(new Date());
	}
    
    public Date extractExpiration(String token) 
    {
        return extractClaim(token, Claims::getExpiration);
    }
    
    public String generateToken(UserDetails userDetails) 
	{
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, userDetails);
	}
    
    private String createToken(Map<String, Object> claims, UserDetails userDetails) 
	{
		return Jwts.builder().setClaims(claims)
		.claim("userName", userDetails.getUsername())
		.claim("role", userDetails.getAuthorities())
		.setSubject("AcessToken").setIssuedAt(new Date(System.currentTimeMillis()))
		.setExpiration(new Date(System.currentTimeMillis() + validityInMilliseconds))
		.signWith(SignatureAlgorithm.HS256, secretkey).compact();
	}
    
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) 
	{
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
    
    private Claims extractAllClaims(String token) 
    {
        return Jwts.parser().setSigningKey(secretkey).parseClaimsJws(token).getBody();
    }
}
