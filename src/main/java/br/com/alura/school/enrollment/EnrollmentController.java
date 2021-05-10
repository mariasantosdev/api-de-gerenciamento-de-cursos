package br.com.alura.school.enrollment;

import br.com.alura.school.course.Course;
import br.com.alura.school.course.CourseRepository;
import br.com.alura.school.user.User;
import br.com.alura.school.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Date;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
class EnrollmentController {

    private final CourseRepository courseRepository;

    private final EnrollmentRepository enrollmentRepository;

    private final UserRepository userRepository;

    EnrollmentController(CourseRepository courseRepository, EnrollmentRepository enrollmentRepository,
                         UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/courses/{code}/enroll")
    ResponseEntity<Void> newEnrollment(@PathVariable("code") String code,
                                       @RequestBody @Valid NewEnrollmentRequest newEnrollmentRequest) {
        Course course = courseRepository.findByCode(code)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND,
                        format("Course with code %s not found", code)));
        User user = userRepository.findByUsername(newEnrollmentRequest.getUsername())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND,
                        format("User with username %s not found", newEnrollmentRequest.getUsername())));

        if(enrollmentRepository.existsByCourseAndUser(course,user)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Enrollment enrollment = new Enrollment(user,course, new Date());
        enrollmentRepository.save(enrollment);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}