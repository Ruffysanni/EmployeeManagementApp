package com.ruffy.EmployeeManagementApp.config;


import com.ruffy.EmployeeManagementApp.service.JWTService;
import com.ruffy.EmployeeManagementApp.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JWTService jwtService;

    @Autowired
    private ApplicationContext applicationContext;

    private UserService getUserService(){
        return applicationContext.getBean(UserService.class);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // Extract token from the request header
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        if(authHeader != null && authHeader.startsWith("Bearer ")){
            // Extract token from the authorization header
            token = authHeader.substring(7);
            username = jwtService.extractUsername(token);
        }

        // f username is extracted and there is no authentication in the current SecurityContext
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = getUserService().loadUserByUsername(username);

            // Validating the token with loaded Details
            if(jwtService.validateToken(token, userDetails)){
                // Creating an authentication token using userDetails
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
