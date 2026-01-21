package presentaion.common;

import business.ICourseService;
import business.impl.CourseServiceImpl;
import model.entity.Course;

import java.util.List;
import java.util.Scanner;

public class CourseView {
    static Scanner sc = new Scanner(System.in);
    private static final ICourseService courseService = new CourseServiceImpl();

    //biểu diễn
    private static void showCourses(List<Course> courses){
        if (courses == null || courses.isEmpty()) {
            System.out.println("Không có khóa học nào");
            return;
        }
        System.out.println("------DANH SÁCH KHÓA HỌC------");
        System.out.printf("%-10s %-20s %-15s %-20s %-20s%n",
                "Id","Tên khóa học", "Số giờ học", "Giảng viên", "Ngày tạo");

        for (Course c : courses) {
            System.out.printf("%-10d %-20s %-15d %-20s %-20s%n",
                    c.getId(),
                    c.getName(),
                    c.getDuration(),
                    c.getInstructor(),
                    c.getCreatedAt().toLocalDateTime().toLocalDate()
            );
        }
    }

    public static void showAllCourses(){
        try {
            List<Course> courses = courseService.showAllCourses();
            if (courses.isEmpty()) {
                System.out.println("Không tìm thấy khóa học nào");
            } else {
                showCourses(courses);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void findCourse() {
        System.out.print("Nhập từ khóa tìm kiếm (bỏ trống để hủy): ");
        String keyword = sc.nextLine().trim();
        if(keyword.isEmpty())  return;

        try {
            List<Course> courses = courseService.findCourse(keyword);
            if (courses.isEmpty()) {
                System.out.println("Không tìm thấy khóa học nào");
            } else {
                showCourses(courses);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sortCourses(){
        boolean idOrName;
        while(true){
            try{
                System.out.println("Sắp xếp theo: ");
                System.out.println("    1. Tên khóa học");
                System.out.println("    2. Id khóa học");
                System.out.println("    3. Thoát");
                System.out.print("Lựa chọn của bạn: ");
                String choice = sc.nextLine().trim();

                if(choice.equals("3") ) {
                    return;
                }
                else if(choice.equals("1")) {
                    idOrName =true;
                    break;
                }
                else if(choice.equals("2")) {
                    idOrName =false;
                    break;
                }
                else {
                    System.out.println("Lựa chọn không hợp lệ");
                }
            }catch(NumberFormatException e){
                System.out.println("Phải nhập số");
            }
        }

        boolean orderBy;
        while(true){
            try{
                System.out.println("Sắp xếp theo: ");
                System.out.println("    1. Từ trên xuống dưới");
                System.out.println("    2. Từ dưới lên trên");
                System.out.println("    3. Thoát");
                System.out.print("Lựa chọn của bạn: ");
                String choice = sc.nextLine().trim();
                if(choice.equals("3") ) {
                    return;
                }
                else if(choice.equals("1")) {
                    orderBy =true;
                    break;
                }
                else if(choice.equals("2")) {
                    orderBy =false;
                    break;
                }
                else {
                    System.out.println("Lựa chọn không hợp lệ");
                }
            } catch(NumberFormatException e){
                System.out.println("Phải nhập số");
            }
        }

        List<Course> courses = courseService.sortListCourse(courseService.showAllCourses(),idOrName,orderBy);
        showCourses(courses);
    }
}
