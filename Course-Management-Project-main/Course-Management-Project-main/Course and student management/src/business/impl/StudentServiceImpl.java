package business.impl;

import business.IStudentService;
import dao.impl.StudentDAOImpl;

public class StudentServiceImpl implements IStudentService {
    static StudentDAOImpl studentDAO = new StudentDAOImpl();

    @Override
    public void updatePassword(int idStudent, String password){
        studentDAO.updatePasswordStudent(idStudent, password);
    }
}
