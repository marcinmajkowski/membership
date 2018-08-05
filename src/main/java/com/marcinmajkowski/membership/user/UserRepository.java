package com.marcinmajkowski.membership.user;

import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface UserRepository extends Repository<User, Long> {

    Optional<User> findByEmailIgnoreCase(String email);

    User save(User user);
}
