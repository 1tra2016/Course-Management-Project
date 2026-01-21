package business.impl;

import business.IEnrollmentService;
import dao.IEnrollmentDAO;
import dao.impl.EnrollmentDAOImpl;
import model.EnrollmentDetail;

import java.util.List;

public class EnrollmentServiceImpl implements IEnrollmentService {
    static IEnrollmentDAO enrollmentDAO = new EnrollmentDAOImpl();
    @Override
    public List<EnrollmentDetail> getAllEnrollments(int idStudent) {
        return enrollmentDAO.getAllEnrollmentDetails(idStudent);
    }

    @Override
    public void enroll(int idStudent, int idCourse) {
        enrollmentDAO.enroll(idStudent,idCourse);
    }

    @Override
    public void cancerEnrollment(int idStudent, int idEnrollment) {
        enrollmentDAO.cancerEnrollment(idStudent, idEnrollment);
    }
}
