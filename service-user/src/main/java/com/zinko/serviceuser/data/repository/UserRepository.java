package com.zinko.serviceuser.data.repository;

import com.zinko.serviceuser.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT user FROM User user WHERE user.email=:email")
    Optional<User> findByEmail(String email);

}
