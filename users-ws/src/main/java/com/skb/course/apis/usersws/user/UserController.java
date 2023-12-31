package com.skb.course.apis.usersws.user;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    @RequestMapping("/profile")
    public String getUserProfile(@AuthenticationPrincipal OAuth2User oAuth2User) {

        if (oAuth2User != null) {
            return "Username: " + oAuth2User.getName() + "<br>" +
                    "User Authorities: " + oAuth2User.getAuthorities();
        } else {
            return null;
        }
    }
}
