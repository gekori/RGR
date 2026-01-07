package com.university.library.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString; // <-- Додайте цей імпорт
import java.util.List;

@Data
@Entity
@Table(name = "subjects")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    @ToString.Exclude // <-- ЦЕЙ РЯДОК ВИРІШУЄ ПРОБЛЕМУ
    private List<Literature> literatureList;
}