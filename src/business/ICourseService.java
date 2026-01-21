package business;
import model.entity.Course;

import java.util.List;

public interface ICourseService {
    List<Course> showAllCourses();
    void createNewCourse(Course course);
    void updateInforCourse(Course course);
    void deleteCourse(int id);
    List<Course> findCourse(String keyword);
    List<Course> sortListCourse(List<Course> courses, boolean idOrName, boolean orderBy);

    boolean checkCourseExist(int id);
}
