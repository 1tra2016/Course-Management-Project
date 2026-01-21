package business.impl;

import business.ICourseService;
import dao.ICourseDAO;
import dao.impl.CourseDAOImpl;
import model.entity.Course;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CourseServiceImpl implements ICourseService {
    private final ICourseDAO courseDAO  = new CourseDAOImpl();

    @Override
    public List<Course> showAllCourses(){
        List<Course> courses = courseDAO.getAllCourses();
        if (courses.isEmpty()) {
            throw new IllegalStateException("Danh sách khóa học trống");
        }
        return courses;
    }

    @Override
    public void createNewCourse(Course course){
        if(checkCourseExist(course.getId())) throw new IllegalStateException("Xảy ra lỗi: ID " + course.getId()+" đã tồn tại");
        if(course.getDuration() == -1) throw new IllegalArgumentException("Thời lượng khóa học không được để trống");
        courseDAO.createNewCourse(course);
    }

    @Override
    public void updateInforCourse(Course course){
        if(course.getName().isEmpty()) course.setName(null);
        if(course.getInstructor().isEmpty()) course.setInstructor(null);
        courseDAO.updateInforCourse(course);
    }

    @Override
    public void deleteCourse(int id){
        if(checkCourseExist(id)==false) throw new IllegalStateException("Xảy ra lỗi: ID " + id+ " không tồn tại");
        courseDAO.deleteCourse(id);
    }

    @Override
    public List<Course> findCourse(String keyword){
        return courseDAO.findCourse(keyword);
    }

    @Override
    public List<Course> sortListCourse(List<Course> courses, boolean idOrName, boolean orderBy) {
        Comparator<Course> comparator;

        if (idOrName) comparator = Comparator.comparing(Course::getId);
        else comparator = Comparator.comparing(Course::getName, String.CASE_INSENSITIVE_ORDER);

        if (!orderBy) {
            comparator = comparator.reversed();
        }
        return courses.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }


    @Override
    public boolean checkCourseExist(int id){
        boolean isexist = courseDAO.checkCourseExist(id);
        if(isexist==true) return true;
        else return false;
    }
}
