package com.university.library.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "literature")
public class Literature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title; // Назва книги

    @Column(nullable = false)
    private String author; // Автор

    private String type; // Тип (підручник, методичка тощо)

    // Література прив'язана до предмету
    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;
}