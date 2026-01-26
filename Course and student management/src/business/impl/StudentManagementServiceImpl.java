package business.impl;
import business.IStudentManagementService;
import dao.impl.StudentDAOImpl;
import model.entity.Student;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StudentManagementServiceImpl implements IStudentManagementService {
    static StudentDAOImpl studentDAO = new StudentDAOImpl();
    @Override
    public List<Student> showAllStudents(){
        List<Student> students = studentDAO.getAllStudents();
        if (students.isEmpty()) {
            throw new IllegalStateException("Danh sách khóa học trống");
        }
        return students;
    }

    @Override
    public void createNewStudent(Student student) {
        if (student.getName() == null || student.getName().trim().isEmpty()) throw new IllegalArgumentException("Tên không được để trống");
        if (student.getDob() == null) throw new IllegalArgumentException("Ngày sinh không được để trống");
        if (student.getEmail() == null || student.getEmail().trim().isEmpty()) throw new IllegalArgumentException("Email không được để trống");
        if (student.getSex() == null) throw new IllegalArgumentException("Giới tính không được để trống");
        if (student.getPhone()==null) throw new IllegalArgumentException("Số điện thoại không được để trống");

        studentDAO.createNewStudent(student);
    }


    @Override
    public void updateInforStudent(Student student){
        if(student.getName().isEmpty()) student.setName(null);
        studentDAO.updateInforStudent(student);
    }

    @Override
    public void deleteStudent(int id){
        if(checkStudentExist(id)==false) throw new IllegalStateException("Xảy ra lỗi: ID " + id+ " không tồn tại");
        studentDAO.deleteStudent(id);
    }

    @Override
    public List<Student> findStudent(String keyword){
        return studentDAO.findStudent(keyword);
    }

    @Override
    public List<Student> sortListStudent(List<Student> students, boolean idOrName, boolean orderBy) {
        Comparator<Student> comparator;

        if (idOrName) comparator = Comparator.comparing(Student::getId);
        else comparator = Comparator.comparing(Student::getName, String.CASE_INSENSITIVE_ORDER);

        if (!orderBy) {
            comparator = comparator.reversed();
        }
        return students.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }


    @Override
    public boolean checkStudentExist(int id){
        boolean isexist = studentDAO.checkStudentExist(id);
        if(isexist==true) return true;
        else return false;
    }

    @Override
    public void resetPasswordStudent(int id){
        studentDAO.resetPasswordStudent(id);
    }
}
