package dao;

import model.entity.Student;

import java.util.List;

public interface IStudentDAO {
    List<Student> getAllStudents();
    void createNewStudent(Student student);
    void updateInforStudent(Student student);
    void deleteStudent(int id);
    List<Student> findStudent(String keyword);

    boolean checkStudentExist(int id);
    void resetPasswordStudent(int id);
    void updatePasswordStudent(int id, String password);

}
