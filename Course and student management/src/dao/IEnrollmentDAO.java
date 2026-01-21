package dao;

import model.EnrollmentDetail;
import model.entity.Enrollment;

import java.util.List;

public interface IEnrollmentDAO {
    List<EnrollmentDetail> getAllEnrollmentDetails(int idStudent);
    void enroll(int idStudent,int idCourse);
    Enrollment getEnrollment(int id);
    void cancel(int id);

    void cancerEnrollment(int idStudent,int idEnrollment);
}
