package business;

import model.EnrollmentDetail;

import java.util.List;

public interface IEnrollmentService {
    List<EnrollmentDetail> getAllEnrollments(int idStudent);
    void enroll(int idStudent,int idCourse);
    void cancerEnrollment(int idStudent,int idEnrollment);
}
