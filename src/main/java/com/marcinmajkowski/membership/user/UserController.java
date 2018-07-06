package com.marcinmajkowski.membership.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/users")
class UserController {

    @GetMapping("/me")
    public Principal getMe(Principal user) {
        return user;
    }
}
