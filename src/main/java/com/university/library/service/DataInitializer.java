package com.university.library.service;

import com.university.library.model.Department;
import com.university.library.model.Literature;
import com.university.library.model.Subject;
import com.university.library.model.User;
import com.university.library.repository.DepartmentRepository;
import com.university.library.repository.LiteratureRepository;
import com.university.library.repository.SubjectRepository;
import com.university.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final SubjectRepository subjectRepository;
    private final LiteratureRepository literatureRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Перевіряємо, чи є користувачі. Якщо немає - ініціалізуємо базу.
        if (userRepository.count() == 0) {
            
            // 1. Створюємо Адміна (пароль буде закодований у Base64)
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin")); // Пароль: admin
            admin.setRole("ADMIN");
            userRepository.save(admin);

            // 2. Створюємо звичайного юзера
            User user = new User();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("user")); // Пароль: user
            user.setRole("USER");
            userRepository.save(user);

            // 3. Створюємо Кафедру
            Department deptCS = new Department();
            deptCS.setName("Computer Science");
            departmentRepository.save(deptCS);

            // 4. Створюємо Предмет
            Subject subjectJava = new Subject();
            subjectJava.setName("Java Programming");
            subjectJava.setDepartment(deptCS);
            subjectRepository.save(subjectJava);

            // 5. Створюємо Літературу
            Literature book1 = new Literature();
            book1.setTitle("Thinking in Java");
            book1.setAuthor("Bruce Eckel");
            book1.setType("Textbook");
            book1.setSubject(subjectJava);
            
            Literature book2 = new Literature();
            book2.setTitle("Spring Boot in Action");
            book2.setAuthor("Craig Walls");
            book2.setType("Guide");
            book2.setSubject(subjectJava);

            literatureRepository.saveAll(List.of(book1, book2));

            System.out.println("---------------------------------------------");
            System.out.println(" БАЗА ДАНИХ УСПІШНО ПРОІНІЦІАЛІЗОВАНА! ");
            System.out.println(" Admin login: admin / pass: admin ");
            System.out.println(" User login:  user  / pass: user  ");
            System.out.println("---------------------------------------------");
        }
    }
}