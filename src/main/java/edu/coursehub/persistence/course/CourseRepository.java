package edu.coursehub.persistence.course;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    // Consultas derivadas (S05)
    Course findByCode(String code);
    List<Course> findByNameContainingIgnoreCase(String name);
    List<Course> findByDepartmentName(String departmentName);

    // JPQL para cursos sin matrículas (S06)
    @Query("""
        select c
        from Course c
        left join c.enrollments e
        where e is null
    """)
    List<Course> findCoursesWithoutEnrollments();

    // Consulta nativa PostgreSQL con ILIKE (S06)
    @Query(value = """
        SELECT *
        FROM course
        WHERE code ILIKE CONCAT('%', :text, '%')
           OR name ILIKE CONCAT('%', :text, '%')
    """, nativeQuery = true)
    List<Course> searchByCodeOrName(@Param("text") String text);
}