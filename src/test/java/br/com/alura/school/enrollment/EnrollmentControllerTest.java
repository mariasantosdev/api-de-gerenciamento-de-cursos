package br.com.alura.school.enrollment;

import br.com.alura.school.course.Course;
import br.com.alura.school.course.CourseRepository;
import br.com.alura.school.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import br.com.alura.school.user.User;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = "classpath:schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class EnrollmentControllerTest {

    private final ObjectMapper jsonMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void should_add_new_enrollment() throws Exception {
        Course course = new Course("spring-01", "framework java", "Making an application with spring");
        courseRepository.save(course);
        User user = new User("alex","alex@gmail.com");
        userRepository.save(user);
        NewEnrollmentRequest newEnrollmentRequest = new NewEnrollmentRequest("alex");
        mockMvc.perform(post("/courses/{code}/enroll", course.getCode())
                .content(jsonMapper.writeValueAsString(newEnrollmentRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .is(201));

    }

    @Test
    void should_validate_enrollment_is_duplicate() throws Exception{
        Course course = new Course("spring-01", "framework java", "Making an application with spring");
        courseRepository.save(course);
        User user = new User("alex","alex@gmail.com");
        userRepository.save(user);
        Enrollment enrollment = new Enrollment(user,course,new Date());
        enrollmentRepository.save(enrollment);
        NewEnrollmentRequest newEnrollmentRequest = new NewEnrollmentRequest("alex");
        mockMvc.perform(post("/courses/{code}/enroll", course.getCode())
                .content(jsonMapper.writeValueAsString(newEnrollmentRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .is(400));
    }

    @Test
    void should_validate_when_user_not_exist() throws Exception{
        Course course = new Course("spring-01", "framework java", "Making an application with spring");
        courseRepository.save(course);
        NewEnrollmentRequest newEnrollmentRequest = new NewEnrollmentRequest("alex");
        mockMvc.perform(post("/courses/{code}/enroll", course.getCode())
                .content(jsonMapper.writeValueAsString(newEnrollmentRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .is(404));
    }

    @Test
    void should_validate_when_course_not_exist() throws Exception{
        User user = new User("alex","alex@gmail.com");
        userRepository.save(user);
        NewEnrollmentRequest newEnrollmentRequest = new NewEnrollmentRequest("alex");
        mockMvc.perform(post("/courses/{code}/enroll", "java01")
                .content(jsonMapper.writeValueAsString(newEnrollmentRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .is(404));
    }

}
