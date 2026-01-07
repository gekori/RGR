package com.university.library.controller;

import com.university.library.model.Literature;
import com.university.library.model.Subject;
import com.university.library.repository.DepartmentRepository;
import com.university.library.repository.LiteratureRepository;
import com.university.library.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class LibraryController {

    // --- ВАЖЛИВО: Перевірте, чи є тут ВСІ ТРИ репозиторії ---
    private final LiteratureRepository literatureRepository;
    private final SubjectRepository subjectRepository;       // <-- Часто забувають цей рядок
    private final DepartmentRepository departmentRepository; // <-- Або цей
    // ---------------------------------------------------------

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/")
    public String homePage(Model model, Authentication authentication) {
        model.addAttribute("books", literatureRepository.findAll());
        
        boolean isAdmin = authentication != null && 
                          authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
        model.addAttribute("isAdmin", isAdmin);
        
        return "index";
    }

    // --- ВИДАЛЕННЯ ---
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        literatureRepository.deleteById(id);
        return "redirect:/";
    }

    // --- РЕДАГУВАННЯ (Зверніть увагу на цей метод) ---
    @GetMapping("/edit/{id}")
    public String editBookForm(@PathVariable Long id, Model model) {
        Literature book = literatureRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        
        model.addAttribute("book", book);
        
        // --- ВАЖЛИВО: Ми повинні передати список предметів, інакше HTML впаде з помилкою 500 ---
        model.addAttribute("subjects", subjectRepository.findAll());
        // ----------------------------------------------------------------------------------------
        
        return "edit";
    }

    @PostMapping("/update/{id}")
    public String updateBook(@PathVariable Long id, @ModelAttribute Literature literature) {
        Literature existingBook = literatureRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        
        existingBook.setTitle(literature.getTitle());
        existingBook.setAuthor(literature.getAuthor());
        existingBook.setType(literature.getType());
        // Оновлюємо предмет (тепер це працює, бо ми вибрали його в формі)
        existingBook.setSubject(literature.getSubject());
        
        literatureRepository.save(existingBook);
        return "redirect:/";
    }

    // --- ДОДАВАННЯ ---
    @GetMapping("/add")
    public String addBookForm(Model model) {
        model.addAttribute("book", new Literature());
        model.addAttribute("subjects", subjectRepository.findAll());
        return "add";
    }

    @PostMapping("/save")
    public String saveBook(@ModelAttribute Literature literature) {
        literatureRepository.save(literature);
        return "redirect:/";
    }

    // --- ДОДАВАННЯ ПРЕДМЕТУ ---
    @GetMapping("/add-subject")
    public String addSubjectForm(Model model) {
        model.addAttribute("subject", new Subject());
        model.addAttribute("departments", departmentRepository.findAll());
        return "add-subject";
    }

    @PostMapping("/save-subject")
    public String saveSubject(@ModelAttribute Subject subject) {
        subjectRepository.save(subject);
        return "redirect:/";
    }
}