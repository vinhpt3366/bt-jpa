CREATE DATABASE jpa;
USE jpa;

CREATE TABLE student(
    id INT AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    age INT CHECK (age > 0),
    
    PRIMARY KEY (id)
);

CREATE TABLE course(
    id INT AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
  	duration DOUBLE CHECK (duration > 0),
  	
    PRIMARY KEY (id)
);


CREATE TABLE registration(
    registration_id INT AUTO_INCREMENT,
    student_id INT NOT NULL,
    course_id INT NOT NULL,
    registration_date DATE ,

    PRIMARY KEY (registration_id),
    FOREIGN KEY (student_id) REFERENCES student(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (course_id) REFERENCES course(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    UNIQUE (student_id, course_id)
);



 INSERT INTO course(title, duration) VALUES 
('course 1', (1)),
('course 2', (2)),
('course 3', (3));

