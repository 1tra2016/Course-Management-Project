CREATE DATABASE course_management;

CREATE TABLE admin (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);
CREATE TABLE student (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    dob DATE NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    sex BOOLEAN NOT NULL, -- true = Nam, false = Nữ
    phone VARCHAR(20),
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE course (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    duration INT NOT NULL,
    instructor VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TYPE enrollment_status AS ENUM (
    'WAITING',
    'DENIED',
    'CANCER',
    'CONFIRMED'
);
CREATE TABLE enrollment (
    id SERIAL PRIMARY KEY,
    student_id INT NOT NULL,
    course_id INT NOT NULL,
    registered_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status enrollment_status DEFAULT 'WAITING',
    CONSTRAINT fk_student FOREIGN KEY (student_id) REFERENCES student(id) ON DELETE CASCADE,
    CONSTRAINT fk_course FOREIGN KEY (course_id) REFERENCES course(id) ON DELETE CASCADE,
    CONSTRAINT unique_enrollment UNIQUE (student_id, course_id)
);


CREATE OR REPLACE PROCEDURE login_as_admin(
    in in_username VARCHAR(50) ,
    in in_password VARCHAR(255),
    out isexist boolean
) LANGUAGE plpgsql AS $$
BEGIN
    SELECT EXISTS (
        SELECT 1 FROM admin WHERE username = in_username AND password = in_password
    ) INTO isexist;
end;
$$;

insert into admin(username,password) values ('admin','password');

CREATE OR REPLACE PROCEDURE show_all_courses(
    OUT cur REFCURSOR
)
LANGUAGE plpgsql AS $$
BEGIN
    OPEN cur FOR
    SELECT id, name, duration, instructor, created_at
    FROM course;
end;
$$;

CREATE OR REPLACE PROCEDURE create_new_course(
    in_name VARCHAR(100),
    in_duration INT,
    in_instructor VARCHAR(100)
) LANGUAGE plpgsql AS $$
BEGIN
    INSERT INTO course(name, duration, instructor) vALUES (in_name,in_duration,in_instructor);
end;
$$;

CREATE OR REPLACE PROCEDURE update_infor_course(
    in_id INT,
    in_name VARCHAR(100) DEFAULT NULL,
    in_duration INT DEFAULT NULL,
    in_instructor VARCHAR(100) DEFAULT NULL
) LANGUAGE plpgsql AS $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM course WHERE id = in_id) THEN
        RAISE EXCEPTION 'Khóa học có id % không tồn tại', in_id;
    END IF;

    PERFORM 1
    FROM course
    WHERE id = in_id
    FOR UPDATE;

    UPDATE course
    SET
        name       = COALESCE(in_name, name),
        duration   = COALESCE(in_duration, duration),
        instructor = COALESCE(in_instructor, instructor)
    WHERE id = in_id;
end;
$$;

CREATE OR REPLACE PROCEDURE check_course_exists(
    IN in_id INT,
    OUT is_exist BOOLEAN
)
    LANGUAGE plpgsql
AS $$
BEGIN
    SELECT EXISTS (
        SELECT 1
        FROM course
        WHERE id = in_id
    ) INTO is_exist;
END;
$$;

CREATE OR REPLACE PROCEDURE delete_course(in_id int)
LANGUAGE plpgsql AS $$
BEGIN
    DELETE FROM course WHERE id=in_id;
end;
$$;
