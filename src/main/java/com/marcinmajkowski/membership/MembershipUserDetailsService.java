package com.marcinmajkowski.membership;

import com.marcinmajkowski.membership.user.UserService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class MembershipUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public MembershipUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService.findByEmailIgnoreCase(username)
                .map(this::userDetailsOf)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    private UserDetails userDetailsOf(com.marcinmajkowski.membership.user.User user) {
        return new User(
                user.getEmail(),
                user.getEncodedPassword(),
                user.isEnabled(),
                true,
                true,
                true,
                Collections.emptyList()
        );
    }
}
