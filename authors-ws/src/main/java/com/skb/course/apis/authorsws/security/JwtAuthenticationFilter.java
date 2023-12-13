package com.skb.course.apis.authorsws.security;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skb.course.apis.authorsws.user.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    /*@Value("bookws.security.jwt.token.secret")
    String secret;*/
    private AuthenticationManager authenticationManager;
    private static String jwtSecret = System.getenv("jwtSecret");

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        User user = null;

        try {
            user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword(), new ArrayList<>()));
        } catch (BadCredentialsException bce) {
            // log event failure event
            throw bce;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                         FilterChain chain, Authentication auth) throws IOException, ServletException {
        User user = (User) auth.getPrincipal();
        String token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1800000))
//                .sign(HMAC512("secret".getBytes()));
                .sign(HMAC512(jwtSecret));
        response.addHeader("Authorization", "Bearer " + token);

    }
}
