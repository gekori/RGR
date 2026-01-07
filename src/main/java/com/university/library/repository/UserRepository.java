package com.university.library.repository;

import com.university.library.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Метод для пошуку користувача по логіну
    Optional<User> findByUsername(String username);
}