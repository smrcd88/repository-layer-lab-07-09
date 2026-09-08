package edu.coursehub.persistence.student;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
    // TODO-STUDENT S05: findByEmail, existsByEmail,
    // findByNameContainingIgnoreCaseAndActiveTrue y paginación.

    Student findByEmail(String email);
    boolean existsByEmail(String email);
    Page<Student> findByNameContainingIgnoreCaseAndActiveTrue(String name, Pageable pageable);
}