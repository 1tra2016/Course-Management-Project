package dao.impl;

import dao.IEnrollmentDAO;
import model.EnrollmentDetail;
import model.enrollmentStatus;
import model.entity.Enrollment;
import utils.DBUtil;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
public class EnrollmentDAOImpl implements IEnrollmentDAO {
    @Override
    public List<EnrollmentDetail> getAllEnrollmentDetails(int idStudent) {
        List<EnrollmentDetail> enrollments = new ArrayList<>();
        String sql = "SELECT * FROM show_enrollment_by_id_student(?)";
        Connection con = null;
        try {
            con = DBUtil.getConnection();
            CallableStatement call = con.prepareCall(sql);
            call.setInt(1, idStudent);

            ResultSet rs = call.executeQuery();
            while (rs.next()) {
                EnrollmentDetail enrollment = new EnrollmentDetail(
                        rs.getInt("enrollment_id"),
                        rs.getInt("course_id"),
                        rs.getString("course_name"),
                        rs.getInt("duration"),
                        rs.getString("instructor"),
                        rs.getTimestamp("registered_at").toLocalDateTime().toLocalDate(),
                        enrollmentStatus.valueOf(rs.getString("status"))
                );
                enrollments.add(enrollment);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            DBUtil.closeConnection(con);
        }
        return enrollments;
    }

    @Override
    public void enroll(int idStudent, int idCourse) {
        String sql = "Call enroll(?,?)";
        Connection con = null;
        try{
            con = DBUtil.getConnection();
            CallableStatement call = con.prepareCall(sql);
            call.setInt(1, idStudent);
            call.setInt(2, idCourse);
            call.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            DBUtil.closeConnection(con);
        }
    }

    @Override
    public Enrollment getEnrollment(int id) {
        return null;
    }

    @Override
    public void cancel(int id) {

    }

    @Override
    public void cancerEnrollment(int idStudent,int idEnrollment) {
        String sql = "Call CALL delete_enrollment_by_student(?, ?)";
        Connection con = null;
        try{
            con = DBUtil.getConnection();
            CallableStatement call = con.prepareCall(sql);
            call.setInt(1, idStudent);
            call.setInt(2, idEnrollment);
            call.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(con);
        }
    }
}
