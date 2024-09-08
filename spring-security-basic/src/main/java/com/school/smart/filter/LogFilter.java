package com.school.smart.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@Slf4j
public class LogFilter extends OncePerRequestFilter {

    @Override
    public void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws IOException, ServletException {
        Collections.list(httpServletRequest.getHeaderNames())
                .forEach(header ->
                        log.info("Header key {} = {}", header, httpServletRequest.getHeader(header)));
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
