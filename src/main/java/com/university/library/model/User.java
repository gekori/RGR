package com.university.library.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password; // Будемо зберігати у Base64 (вимога завдання)

    @Column(nullable = false)
    private String role; // ROLE_ADMIN або ROLE_USER
}