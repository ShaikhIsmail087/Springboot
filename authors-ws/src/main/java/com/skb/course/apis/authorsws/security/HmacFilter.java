package com.skb.course.apis.authorsws.security;

import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class HmacFilter extends BasicAuthenticationFilter {

    private static Map<String, String> hmacUserCredentials = new HashMap<> ();
    static {
        hmacUserCredentials.put("partner-1", "partner-1-secret");
        hmacUserCredentials.put("partner-2", "partner-2-secret");
        hmacUserCredentials.put("partner-3", "partner-3-secret");
    }

    public HmacFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        String hmacHeader = request.getHeader("X-HMAC-Value");

        if(hmacHeader == null || hmacHeader.equals("")) {
            chain.doFilter(request, response);
            return;
        }

        String username = request.getHeader("X-Username");

        if(username != null || !username.equals("")) {
            String secret = hmacUserCredentials.get(username);
            String httpVerb = request.getMethod();
            String path = request.getServletPath();
            String dateHeader = request.getHeader("X-Date");

            String base64OfHmacValue = calculateBase64andHmacValue(httpVerb, path, dateHeader, secret);
            UsernamePasswordAuthenticationToken authenticationToken = null;
            if(base64OfHmacValue != null && base64OfHmacValue.equals(hmacHeader)) {
                authenticationToken = new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
            }

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            chain.doFilter(request, response);
        } else {
            return;
        }


    }

    private String calculateBase64andHmacValue(String httpVerb, String path, String dateHeader, String secret) {

        if(httpVerb == null || path == null || dateHeader == null || secret == null) {
            return null;
        }

        String stringToSign = httpVerb + "::" + path + "::" + dateHeader;
        HmacUtils hmacUtils = new HmacUtils(HmacAlgorithms.HMAC_SHA_256, secret);
        String hmacHex = hmacUtils.hmacHex(stringToSign);
        return Base64.getEncoder().encodeToString(hmacHex.getBytes());
    }
}
