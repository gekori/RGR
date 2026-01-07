package com.university.library.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString; // <-- Додайте цей імпорт
import java.util.List;

@Data
@Entity
@Table(name = "departments")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    @ToString.Exclude // <-- ЦЕЙ РЯДОК ВИРІШУЄ ПРОБЛЕМУ
    private List<Subject> subjects;
}