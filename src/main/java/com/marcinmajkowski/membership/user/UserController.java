package com.marcinmajkowski.membership.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
class UserController {

    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public Principal getMe(Principal user) {
        return user;
    }

    @PostMapping
    public Map createUser(@RequestBody CreateUserForm createUserForm) {
        userService.createUser(createUserForm);
        return Collections.emptyMap();
    }
}
