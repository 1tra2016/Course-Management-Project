package dao.impl;

import dao.IStudentDAO;
import model.entity.Student;
import utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAOImpl implements IStudentDAO {
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM show_all_students()";
        Connection con = null;
        try {
            con = DBUtil.getConnection();
            CallableStatement call = con.prepareCall(sql);

            ResultSet rs = call.executeQuery();
            while (rs.next()) {
                Student student = new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDate("dob").toLocalDate(),
                        rs.getString("email"),
                        rs.getBoolean("sex"),
                        rs.getString("phone"),
                        rs.getTimestamp("created_at")
                );
                students.add(student);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            DBUtil.closeConnection(con);
        }
        return students;
    }

    @Override
    public void createNewStudent(Student student) {
        String sql = "CALL create_new_student(?,?,?,?,?)";
        Connection con = null;
        try {
            con = DBUtil.getConnection();
            CallableStatement call = con.prepareCall(sql);

            call.setString(1,student.getName());
            call.setDate(2, Date.valueOf(student.getDob()));
            call.setString(3,student.getEmail());
            call.setBoolean(4,student.getSex());
            call.setString(5,student.getPhone());

            call.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(con);
        }
    }

    @Override
    public void updateInforStudent(Student student){
        String sql ="CALL update_infor_student(?,?,?,?,?,?,?)";
        try{
            Connection con = DBUtil.getConnection();
            CallableStatement call = con.prepareCall(sql);

            call.setInt(1,student.getId());
            call.setString(2, student.getName());
            call.setString(4,student.getEmail());
            call.setString(6,student.getPhone());
            call.setString(7,student.getPassword());

            if (student.getDob() != null) call.setDate(3, Date.valueOf(student.getDob()));
            else call.setNull(3,Types.DATE);
            if (student.getSex() != null) call.setBoolean(5, student.getSex());
            else call.setNull(5, Types.BOOLEAN);

            call.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void deleteStudent(int id){
        String sql ="CALL delete_student(?)";
        Connection con = null;
        try{
            con = DBUtil.getConnection();
            CallableStatement call = con.prepareCall(sql);
            call.setInt(1,id);
            call.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            DBUtil.closeConnection(con);
        }
    }

    @Override
    public List<Student> findStudent(String keyword){
        List<Student> students = new ArrayList<>();
        Connection con = null;
        try{
            con = DBUtil.getConnection();
            CallableStatement call = con.prepareCall("SELECT * FROM find_student(?)");
            con.setAutoCommit(false);

            call.setString(1,keyword);
            call.executeQuery();
            ResultSet  rs = call.getResultSet();
            while (rs.next()) {
                students.add(new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDate("dob").toLocalDate(),
                        rs.getString("email"),
                        rs.getBoolean("sex"),
                        rs.getString("phone"),
                        rs.getTimestamp("created_at")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(con);
        }
        return students;
    }

    @Override
    public boolean checkStudentExist(int id) {
        String sql = "call check_student_exists(?,?)";
        Connection con = null;

        try {
            con = DBUtil.getConnection();
            CallableStatement call = con.prepareCall(sql);
            call.setInt(1, id);
            call.registerOutParameter(2, Types.BOOLEAN);

            call.execute();
            return  call.getBoolean(2);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(con);
        }
        return false;
    }

    @Override
    public void resetPasswordStudent(int id) {
        String sql = "CALL reset_password_student(?)";
        Connection con = null;
        try{
            con = DBUtil.getConnection();
            CallableStatement call = con.prepareCall(sql);
            call.setInt(1,id);
            call.executeUpdate();
        } catch(Exception e){
            e.printStackTrace();
        } finally{
            DBUtil.closeConnection(con);
        }
    }

    @Override
    public void updatePasswordStudent(int id, String password) {

    }
}
