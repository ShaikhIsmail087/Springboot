package com.skb.course.apis.booksws.security;

import com.skb.course.apis.booksws.user.User;
import com.skb.course.apis.booksws.user.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserService userService;

    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userService.getUserByUsername(username);

        if(user != null) {
             return user;
        } else {
            // log this
            throw new UsernameNotFoundException(username + " does not exist");
        }
    }
}
