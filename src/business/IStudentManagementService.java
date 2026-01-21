package business;

import model.entity.Student;

import java.util.List;

public interface IStudentManagementService {
    List<Student> showAllStudents();
    void createNewStudent(Student student);
    void updateInforStudent(Student student);
    void deleteStudent(int id);
    List<Student> findStudent(String keyword);
    List<Student> sortListStudent(List<Student> students, boolean idOrName, boolean orderBy);

    boolean checkStudentExist(int id);
    void resetPasswordStudent(int id);
}
