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
    sex BOOLEAN NOT NULL,
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

insert into admin(username,password) values ('1','1');

CREATE OR REPLACE FUNCTION show_all_courses()
    RETURNS TABLE (
                      id INT,
                      name VARCHAR,
                      duration INT,
                      instructor VARCHAR,
                      created_at TIMESTAMP
                  )
    LANGUAGE plpgsql AS $$
BEGIN
    RETURN QUERY
    SELECT c.id, c.name, c.duration, c.instructor, c.created_at
    FROM course c;
END;
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

    -- chỉ cập nhất name nếu không null
    IF in_name IS NOT NULL THEN
        UPDATE course
        SET name = in_name
        WHERE id = in_id;
    END IF;

    -- chỉ cập nhật dủation nếu không bằng -1
    IF in_duration != -1 THEN
        UPDATE course
        SET duration = in_duration
        WHERE id = in_id;
    END IF;

    -- chỉ cập nhật íntructor nếu không null
    IF in_instructor IS NOT NULL THEN
        UPDATE course
        SET instructor = in_instructor
        WHERE id = in_id;
    END IF;
end;
$$;

CREATE OR REPLACE PROCEDURE check_course_exists(
    IN p_id INT,
    OUT isexits boolean
) LANGUAGE plpgsql AS $$
BEGIN
    SELECT EXISTS (
        SELECT 1 FROM course WHERE id = p_id
    ) INTO isexits;
end;
$$;



CREATE OR REPLACE PROCEDURE delete_course(in_id int)
LANGUAGE plpgsql AS $$
BEGIN
    DELETE FROM course WHERE id=in_id;
end;
$$;

CREATE OR REPLACE FUNCTION find_course(keyword varchar(50))
    RETURNS TABLE (
        id INT,
        name VARCHAR,
        duration INT,
        instructor VARCHAR,
        created_at TIMESTAMP
    )
LANGUAGE plpgsql AS $$
BEGIN
    RETURN QUERY
        SELECT c.id, c.name, c.duration, c.instructor, c.created_at
        FROM course c
        WHERE
            c.name ILIKE '%' || keyword || '%'
           OR c.instructor ILIKE '%' || keyword || '%';
END;
$$;

CREATE OR REPLACE FUNCTION show_all_students()
RETURNS TABLE(id int, name VARCHAR(100), dob DATE, email VARCHAR(100) , sex BOOLEAN, phone VARCHAR(20), created_at TIMESTAMP)
LANGUAGE plpgsql AS $$
BEGIN
    RETURN QUERY
    SELECT s.id, s.name, s.dob , s.email, s.sex, s.phone, s.created_at
    FROM student s;
END
$$;

CREATE OR REPLACE PROCEDURE create_new_student(
    in_name VARCHAR(100),
    in_dob DATE,
    in_email VARCHAR(100) ,
    in_sex BOOLEAN,
    in_phone VARCHAR(20)
) LANGUAGE plpgsql AS $$
BEGIN
    INSERT INTO student(name, dob, email, sex, phone, password)
    VALUES (in_name, in_dob, in_email, in_sex, in_phone, '@123');
end;
$$;

CREATE OR REPLACE PROCEDURE update_infor_student(
    in_id INT,
    in_name VARCHAR(100) DEFAULT NULL,
    in_dob DATE DEFAULT NULL,
    in_email VARCHAR(100) DEFAULT NULL,
    in_sex BOOLEAN DEFAULT NULL,
    in_phone VARCHAR(20) DEFAULT NULL,
    in_password VARCHAR(255) DEFAULT NULL
)
    LANGUAGE plpgsql AS $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM student WHERE id = in_id) THEN
        RAISE EXCEPTION 'Student có id % không tồn tại', in_id;
    END IF;

    IF in_name IS NOT NULL THEN
        UPDATE student SET name = in_name WHERE id = in_id;
    END IF;

    IF in_dob IS NOT NULL THEN
        UPDATE student SET dob = in_dob WHERE id = in_id;
    END IF;

    IF in_email IS NOT NULL THEN
        UPDATE student SET email = in_email WHERE id = in_id;
    END IF;

    IF in_sex IS NOT NULL THEN
        UPDATE student SET sex = in_sex WHERE id = in_id;
    END IF;

    IF in_phone IS NOT NULL THEN
        UPDATE student SET phone = in_phone WHERE id = in_id;
    END IF;

    IF in_password IS NOT NULL THEN
        UPDATE student SET password = in_password WHERE id = in_id;
    END IF;
end;
$$;

CREATE OR REPLACE PROCEDURE check_student_exists(
    IN p_id INT,
    OUT isexists BOOLEAN
)
    LANGUAGE plpgsql AS $$
BEGIN
    SELECT EXISTS (
        SELECT 1 FROM student WHERE id = p_id
    ) INTO isexists;
end;
$$;

CREATE OR REPLACE PROCEDURE delete_student(
    in_id INT
)
    LANGUAGE plpgsql AS $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM student WHERE id = in_id) THEN
        RAISE EXCEPTION 'Student có id % không tồn tại', in_id;
    END IF;

    DELETE FROM student WHERE id = in_id;
end;
$$;

CREATE OR REPLACE FUNCTION find_student(keyword VARCHAR(50))
    RETURNS TABLE(
                     id INT,
                     name VARCHAR(100),
                     dob DATE,
                     email VARCHAR(100),
                     sex BOOLEAN,
                     phone VARCHAR(20),
                     created_at TIMESTAMP
                 )
    LANGUAGE plpgsql AS $$
BEGIN
    RETURN QUERY
        SELECT s.id, s.name, s.dob, s.email, s.sex, s.phone, s.created_at
        FROM student s
        WHERE
            s.name ILIKE '%' || keyword || '%'
           OR s.email ILIKE '%' || keyword || '%'
           OR CAST(s.id AS TEXT) ILIKE '%' || keyword || '%';
end;
$$;

CREATE OR REPLACE PROCEDURE reset_password_student(in_id int) LANGUAGE plpgsql AS $$
BEGIN
    UPDATE student SET password = '@123' WHERE id =in_id;
end;
$$;

CREATE OR REPLACE PROCEDURE login_as_student(
    in in_phone VARCHAR(50) ,
    in in_password VARCHAR(255),
    out id_student int
) LANGUAGE plpgsql AS $$
BEGIN

    SELECT id
    FROM student
    WHERE phone = in_phone AND password = in_password
    INTO id_student;
end;
$$;

insert into student(name ,dob,email,sex,phone,password)
values('Nguyen Van A','01/01/2000','abc@gmail.com',false,'1','1');

CREATE OR REPLACE FUNCTION show_enrollment_by_id_student(p_id_student INT)
RETURNS TABLE (
    enrollment_id INT,

    course_id INT,
    course_name VARCHAR,
    duration INT,
    instructor VARCHAR,

    registered_at TIMESTAMP,
    status enrollment_status
)LANGUAGE plpgsql AS $$
BEGIN
    RETURN QUERY
    SELECT e.id,  c.id, c.name, c.duration, c.instructor,   e.registered_at, e.status
    FROM enrollment e JOIN course c ON e.course_id = c.id
    WHERE e.student_id = p_id_student;
end;
$$;

CREATE OR REPLACE PROCEDURE enroll(
    in_student_id int,
    in_course_id int
) LANGUAGE plpgsql AS $$
BEGIN
    INSERT INTO enrollment(student_id,course_id)
    VALUES(in_student_id,in_course_id);
end;
$$;

CREATE OR REPLACE PROCEDURE check_enrollment_status(
    IN in_id_student INT,
    IN in_id_enrollment INT,
    OUT out_status enrollment_status
) LANGUAGE plpgsql AS $$
BEGIN
    SELECT status FROM enrollment WHERE id = in_id_enrollment AND student_id = in_id_student
    INTO out_status;
end;
$$;

CREATE OR REPLACE PROCEDURE delete_enrollment_by_student(
    p_id_student INT,
    p_id_enrollment INT
)LANGUAGE plpgsql AS $$
DECLARE
    v_status enrollment_status;
BEGIN
    --kiểm tra tônf tại
    IF NOT EXISTS (
        SELECT 1 FROM student WHERE id = p_id_student
    ) THEN
        RAISE EXCEPTION 'Student id % không tồn tại', p_id_student;
    END IF;

    SELECT status
    INTO v_status
    FROM enrollment
    WHERE id = p_id_enrollment AND student_id = p_id_student;
    IF NOT FOUND THEN
        RAISE EXCEPTION
            'Student id % không chứa enrollment id % ',
            p_id_student, p_id_enrollment;
    END IF;

    IF v_status <> 'WAITING' THEN
        RAISE EXCEPTION
            'Chỉ được hủy enrollment khi trạng thái là WAITING (hiện tại: %)',
            v_status;
    END IF;

    DELETE FROM enrollment
    WHERE id = p_id_enrollment AND student_id = p_id_student;
end;
$$;
