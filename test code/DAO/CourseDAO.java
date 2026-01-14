package DAO;

import connect.DBConnection;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import entity.Course;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class CourseDAO {
    public static List<Course> getAllCourses(){
        List<Course> courses = new ArrayList<>();
        String sql ="call show_all_courses(?)";
        try(Connection con = DBConnection.getConnection();
            CallableStatement cs = con.prepareCall(sql)
        ){
            con.setAutoCommit(false);

            cs.registerOutParameter(1, Types.REF_CURSOR);
            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(1)) {
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
            }

            con.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return courses;
    }

    public static boolean createNewCourse(String name, int duration, String instructor){
        String sql ="call create_new_course(?,?,?)";
        try(Connection con = DBConnection.getConnection();
            CallableStatement cs = con.prepareCall(sql)
        ){
            cs.setString(1,name);
            cs.setInt(2,duration);
            cs.setString(3,instructor);
            cs.execute();
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static boolean checkCourseExists(int id){
        String sql ="call check_course_exists(?,?)";
        try(Connection con = DBConnection.getConnection();
            CallableStatement cs = con.prepareCall(sql)
        ){
            con.setAutoCommit(false);

            cs.setInt(1,id);
            cs.registerOutParameter(2, Types.BOOLEAN);
            cs.execute();
            return cs.getBoolean(2);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateInforCourse(int id,String name, int duration, String instructor){
        String sql ="call update_infor_course(?,?,?,?)";
        try(Connection con = DBConnection.getConnection();
            CallableStatement cs = con.prepareCall(sql)
        ){
            cs.setInt(1,id);

            if (name == null || name.isBlank()) cs.setNull(2, Types.VARCHAR);
            else cs.setString(2, name);

            if (duration == -1) cs.setNull(3, Types.INTEGER);
            else cs.setInt(3, duration);

            if (instructor == null || instructor.isBlank()) cs.setNull(4, Types.VARCHAR);
            else cs.setString(4, instructor);

            cs.execute();
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public  static void deleteCourse(int id){
        String sql ="call delete_course(?)";
        try(Connection con = DBConnection.getConnection();
            CallableStatement cs = con.prepareCall(sql)
        ){
            cs.setInt(1,id);
            cs.execute();
            return;
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
