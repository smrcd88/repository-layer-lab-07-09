package edu.coursehub.persistence.enrollment;

import edu.coursehub.persistence.course.Course;
import edu.coursehub.persistence.course.CourseRepository;
import edu.coursehub.persistence.student.Student;
import edu.coursehub.persistence.student.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    // 1. Inyectar repositorios mediante constructor.
    public EnrollmentService(EnrollmentRepository enrollmentRepository,
                             StudentRepository studentRepository,
                             CourseRepository courseRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    // 2. Crear enroll(studentId, courseId).
    @Transactional // 3. Marcar la operación como transaccional.
    public void enroll(Long studentId, Long courseId) {
        // 4. Validar estudiante/curso.
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        // 5. Evitar matrícula duplicada en la capa de negocio.
        if (enrollmentRepository.existsByStudentIdAndCourseId(studentId, courseId)) {
            throw new IllegalStateException("Duplicate enrollment is not allowed");
        }

        // Crear y guardar la nueva matrícula.
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setEnrolledAt(java.time.Instant.now());
        enrollment.setStatus(EnrollmentStatus.ACTIVE);

        enrollmentRepository.save(enrollment);
    }
}