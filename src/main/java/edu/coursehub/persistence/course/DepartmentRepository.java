package edu.coursehub.persistence.course;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    // Buscar un departamento por su nombre
    Department findByName(String name);

    // Verificar si existe un departamento con un nombre específico
    boolean existsByName(String name);

    // Obtener todos los departamentos ordenados alfabéticamente
    List<Department> findAllByOrderByNameAsc();
}