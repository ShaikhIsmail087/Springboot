package com.skb.course.apis.authorsws.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    /*@Value("bookws.security.jwt.token.secret")
    String secret;*/


    private static String jwtSecret = System.getenv("jwtSecret");

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response
            , FilterChain chain) throws IOException, ServletException {
        // Getting the header from the request
        String authHeader = request.getHeader("Authorization");

        // Validate the Authorization header
        if (authHeader == null || authHeader.trim().length() == 0 || !authHeader.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }
        //auth header is present and is in valid  format
        UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(authHeader);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);

    }

    private UsernamePasswordAuthenticationToken getAuthentication(String authHeader) {

        if (authHeader != null) {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(jwtSecret))
                    .build()
                    .verify(authHeader.replace("Bearer ", ""));
            if (decodedJWT != null) {
                String userNameFromJwt = decodedJWT.getSubject();

                if (userNameFromJwt != null) {
                    Set<SimpleGrantedAuthority> authorities = Stream.of(decodedJWT.getClaim("roles")
                                    .asString().split(","))
                                    .map(a -> new SimpleGrantedAuthority(a))
                                    .collect(Collectors.toSet());
//                UserDetails userDetails = userDetailsService.loadUserByUsername(userNameFromJwt);
                    return new UsernamePasswordAuthenticationToken(userNameFromJwt, null,
                            authorities);
                }
            }
        }
        return null;
    }
}
