package dao.impl;

import dao.ICourseDAO;
import model.entity.Course;
import utils.DBUtil;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
public class CourseDAOImpl implements ICourseDAO {
    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM show_all_courses()";
        Connection con = null;
        try {
            con = DBUtil.getConnection();
            CallableStatement call = con.prepareCall(sql);

            ResultSet rs = call.executeQuery();
            while (rs.next()) {
                Course course = new Course(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("duration"),
                        rs.getString("instructor"),
                        rs.getTimestamp("created_at")
                );
                courses.add(course);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            DBUtil.closeConnection(con);
        }
        return courses;
    }

    @Override
    public void createNewCourse(Course course) {
        String sql = "CALL create_new_course(?,?,?)";
        Connection con = null;
        try {
            con = DBUtil.getConnection();
            CallableStatement call = con.prepareCall(sql);

            call.setString(1, course.getName());
            call.setInt(2, course.getDuration());
            call.setString(3, course.getInstructor());

            call.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(con);
        }
    }

    @Override
    public void updateInforCourse(Course course){
        String sql ="CALL update_infor_course(?,?,?,?)";
        try{
            Connection con = DBUtil.getConnection();
            CallableStatement cs = con.prepareCall(sql);

            cs.setInt(1,course.getId());
            cs.setString(2, course.getName());
            cs.setInt(3, course.getDuration());
            cs.setString(4, course.getInstructor());

            cs.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void deleteCourse(int id){
        String sql ="CALL delete_course(?)";
        Connection con = null;
        try{
            con = DBUtil.getConnection();
            CallableStatement cs = con.prepareCall(sql);
            cs.setInt(1,id);
            cs.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            DBUtil.closeConnection(con);
        }
    }

    @Override
    public List<Course> findCourse(String keyword){
        List<Course> courses = new ArrayList<>();
        Connection con = null;
        try{
            con = DBUtil.getConnection();
            CallableStatement cs = con.prepareCall("SELECT * FROM find_course(?)");
            con.setAutoCommit(false);

            cs.setString(1,keyword);
            cs.executeQuery();
            ResultSet  rs = cs.getResultSet();
            while (rs.next()) {
                courses.add(new Course(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("duration"),
                        rs.getString("instructor"),
                        rs.getTimestamp("created_at")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(con);
        }
        return courses;
    }

    @Override
    public boolean checkCourseExist(int id) {
        String sql = "call check_course_exists(?,?)";
        Connection con = null;

        try {
            con = DBUtil.getConnection();
            CallableStatement call = con.prepareCall(sql);
            call.setInt(1, id);
            call.registerOutParameter(2, Types.BOOLEAN);
            call.execute();

            return call.getBoolean(2);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(con);
        }
        return false;
    }
}
