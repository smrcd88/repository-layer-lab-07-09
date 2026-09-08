package edu.coursehub.persistence.enrollment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    // TODO-STUDENT S05: exists/count/find derivados.
    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);
    long countByStatus(EnrollmentStatus status);

    // TODO-STUDENT S06: consultas JPQL.
    @Query("""
        select e.student
        from Enrollment e
        where e.course.id = :courseId
          and e.status = :status
    """)
    List<Student> findStudentsByCourseAndStatus(Long courseId, EnrollmentStatus status);

    // TODO-STUDENT S11: resolver fetch para evitar N+1.
    @Query("""
        select e
        from Enrollment e
        join fetch e.student
        join fetch e.course
        where e.course.id = :courseId
    """)
    List<Enrollment> findEnrollmentsWithStudentAndCourse(Long courseId);
}