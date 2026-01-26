package presentaion.student;

import business.impl.EnrollmentServiceImpl;
import model.EnrollmentDetail;
import presentaion.common.CourseInput;
import presentaion.common.CourseView;

import java.util.List;
import java.util.Scanner;

public class StudentView {
    static Scanner sc = new Scanner(System.in);
    static CourseView courseView = new CourseView();
    static CourseInput courseInput = new CourseInput();
    static EnrollmentServiceImpl enrollmentService = new EnrollmentServiceImpl();
    public static void MenuStudent(int idStudent){
        while(true){
            System.out.println("========MENU STUDENT========");
            System.out.println("1. Xem danh sách khóa học đang có (và sắp xếp)\n" +
                    "2. Tìm kiếm khóa học theo từ khóa (tên khóa học và tên giảng viên)\n" +
                    "3. Đăng kí khóa học\n" +
                    "4. Xem danh sách khóa học đã đăng ký\n"+
                    "5. Hủy đăng ký khóa học\n" +
                    "6. Cập nhật mật khẩu mới\n" +
                    "7. Đăng xuất");
            System.out.print("Nhập lựa chọn: ");
            String choice = sc.nextLine();
            switch(choice){
                case "1":
                    showAllCourse();
                    break;
                case "2":
                    findCourse();
                    break;
                case "3":
                    enroll(idStudent);
                    break;
                case "4":
                    showAllEnrollment(idStudent);
                    break;
                case "5":
                    cancerEnrollment(idStudent);
                    System.out.println("Đã đăng xuất");
                    return;
                default:
                    System.out.println("Vui lòng nhập số");
                    break;
            }
        }
    }
    private static void showAllCourse(){
        courseView.showAllCourses();
        CourseView.sortCourses();
    }
    private static void findCourse(){
        courseView.findCourse();
    }

    private static void enroll(int idStudent){
        int idCourse = courseInput.inputId("Nhập id khóa học muốn đăng ký: ");
        enrollmentService.enroll(idStudent,idCourse);
    }

    private static void showAllEnrollment(int idStudent){
        List<EnrollmentDetail> enrollments = enrollmentService.getAllEnrollments(idStudent);
        showEnrollments(enrollments);
    }

    private static void cancerEnrollment(int idStudent){
        int idEnrollment;

        while(true){
            try{
                idEnrollment = Integer.parseInt(sc.nextLine());
                break;
            } catch(NumberFormatException e ){
                System.out.println("Phải nhập số");
            }
        }
        enrollmentService.cancerEnrollment(idStudent,idEnrollment);
    }

    private static void showEnrollments(List<EnrollmentDetail> enrollments) {
        if (enrollments == null || enrollments.isEmpty()) {
            System.out.println("Không có khóa học nào đã đăng ký.");
            return;
        }

        System.out.println("------DANH SÁCH KHÓA HỌC ĐÃ ĐĂNG KÝ------");
        System.out.printf(
                "%-12s %-12s %-20s %-10s %-15s %-15s %-12s%n",
                "Id đăng ký", "Id khóa học", "Tên khóa học", "Số giờ học", "Giảng viên", "Ngày đăng ký", "Trạng thái"
        );

        for (EnrollmentDetail e : enrollments) {
            System.out.printf(
                    "%-12d %-12d %-20s %-10d %-15s %-15s %-12s%n",
                    e.getEnrollmentId(),
                    e.getCourseId(),
                    e.getCourseName(),
                    e.getDuration(),
                    e.getInstructor(),
                    e.getRegisteredAt(),
                    e.getStatus()
            );
        }
    }


}


