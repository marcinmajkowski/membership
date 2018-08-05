package com.marcinmajkowski.membership.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public Optional<User> findByEmailIgnoreCase(String email) {
        return userRepository.findByEmailIgnoreCase(email);
    }

    // FIXME implement email activation with separate table
    @Transactional
    public void createUser(CreateUserForm createUserForm) {
        User user = new User();
        user.setEmail(createUserForm.getEmail());
        user.setEncodedPassword(passwordEncoder.encode(createUserForm.getPassword()));
        user.setEnabled(false);
        userRepository.save(user);
    }
}
