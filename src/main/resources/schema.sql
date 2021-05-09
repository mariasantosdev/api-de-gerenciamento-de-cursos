DROP TABLE IF EXISTS Enrollment;
DROP TABLE IF EXISTS User;
DROP TABLE IF EXISTS Course;

CREATE TABLE User (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(20) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL
);

CREATE TABLE Course (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(10) NOT NULL UNIQUE,
    name VARCHAR(20) NOT NULL UNIQUE,
    description VARCHAR(500)
);
CREATE TABLE Enrollment(
 id BIGINT AUTO_INCREMENT PRIMARY KEY,
 date_enrollment TIMESTAMP NOT NULL,
 user_id BIGINT,
 course_id BIGINT,
FOREIGN KEY (user_id) references User (id),
FOREIGN KEY (course_id) references Course (id),
CONSTRAINT enrollment_user_course_uq UNIQUE (user_id,course_id)
);
