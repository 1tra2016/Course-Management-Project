package dao;

import model.entity.Course;

import java.util.List;

public interface ICourseDAO {

    List<Course> getAllCourses();
    void createNewCourse(Course course);
    void updateInforCourse(Course course);
    void deleteCourse(int id);
    List<Course> findCourse(String keyword);

    boolean checkCourseExist(int id);
}
