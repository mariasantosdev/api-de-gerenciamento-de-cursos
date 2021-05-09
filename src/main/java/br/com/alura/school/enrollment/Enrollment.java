package br.com.alura.school.enrollment;

import br.com.alura.school.course.Course;
import br.com.alura.school.user.User;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
public class Enrollment {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Course course;

    private Date dateEnrollment;

    @Deprecated
    protected Enrollment() {}

    public Enrollment(User user, Course course, Date dateEnrollment) {
        this.user = user;
        this.course = course;
        this.dateEnrollment = dateEnrollment;
    }

    User getUser() { return user; }

    Course getCourse() {
        return course;
    }

    Date getDateEnrollment() {
        return dateEnrollment;
    }
}
