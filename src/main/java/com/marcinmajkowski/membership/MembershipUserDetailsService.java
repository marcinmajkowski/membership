package com.marcinmajkowski.membership;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class MembershipUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO password: "password"
        return new User(username, "$2a$04$Q94N5exUn9uK4vKS8oxx0.hmKRhRYzm39bPoyv5JxN4SCih9oyN66", Collections.emptyList());
    }
}
