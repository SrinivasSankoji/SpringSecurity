package com.chary.bhaumik.jwt.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.chary.bhaumik.jwt.util.JWTUtil;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JWTRequestFilter extends OncePerRequestFilter
{
	@Autowired
	@Lazy
	UserDetailsService userDetailsService;
	
	@Autowired
	JWTUtil jwtUtil;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, 
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException 
	{
		final String authorizationHeader = request.getHeader("Authorization");
        String userName = null;
        String jwtToken = "";
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) 
		{
			try {
				jwtToken = authorizationHeader.substring(7);
				userName = jwtUtil.extractUsername(jwtToken);
			} catch (ExpiredJwtException exception) {
				exception.printStackTrace();
			}
		}
        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) 
        {
        	UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
            if (jwtUtil.validateToken(jwtToken)) 
            {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
