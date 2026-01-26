package presentaion.admin;

import business.ICourseService;
import business.impl.CourseServiceImpl;
import presentaion.common.CourseInput;
import presentaion.common.CourseView;

import java.util.Scanner;

public class CourseManagementView {
    private static final ICourseService courseService = new CourseServiceImpl();
    private static final CourseView courseView = new CourseView();
    private static final CourseInput courseInput = new CourseInput();
    static Scanner sc =  new Scanner(System.in);
    public static void courseManagement(){
        while(true){
            System.out.println("========Chức năng quản lý khóa học========");
            System.out.println("1. Hiển thị danh sách khóa học");
            System.out.println("2. Thêm mới khóa học" );
            System.out.println("3. Chỉnh sửa thông tin khóa học" );
            System.out.println("4. Xóa khóa học" );
            System.out.println("5. Tìm kiếm khóa học theo tên (khóa học và giảng viên)" );
            System.out.println("6. Sắp xếp khóa học theo tên hoặc id" );
            System.out.println("7. Quay về menu chính");
            System.out.print("Nhập lựa chọn: ");
            String choice = sc.nextLine();
            switch(choice){
                case "1":
                    showAllCourses();
                    break;
                case "2":
                    createNewCourse();
                    break;
                case "3":
                    updateInforCourse();
                    break;
                case "4":
                    deleteCourse();
                    break;
                case "5":
                    findCourse();
                    break;
                case "6":
                    sortCourses();
                    break;
                case "7":
                    return;
                default:
                    System.out.println("Vui lòng nhập số");
                    break;
            }
        }
    }

    //1
    private static void showAllCourses(){
        courseView.showAllCourses();
    }

    //2
    private static void createNewCourse(){
        System.out.println("------Chức năng thêm khóa học mới------");
        String name;
        while(true){
            System.out.print("Nhập tên khóa học: ");
            name = sc.nextLine().trim();
            if(!name.isEmpty()) break;
            System.out.println("Tên khóa học không được để trống");
        }

        Integer duration = courseInput.inputDuration("Nhập thời lượng khóa học (đơn vị giờ): ");

        String  instructor;
        while(true){
            System.out.print("Nhập tên giảng viên khóa học: ");
            instructor = sc.nextLine();
            if(!instructor.isEmpty()) break;
            System.out.println("Tên giảng viên không được để trống");
        }

        try{
            model.entity.Course course = new model.entity.Course();
            course.setName(name);
            course.setDuration(duration);
            course.setInstructor(instructor);
            courseService.createNewCourse(course);
            System.out.println("Thêm khóa học thành công");
        } catch(Exception e){
            e.printStackTrace();
        }

    }

    //3
    private static void updateInforCourse(){
        System.out.println("------Chức năng đổi thông tin khóa học------");

        int id = CourseInput.inputId("Nhập id khóa học muốn cập nhật: ");

        System.out.println("Chọn trường thông tin muốn cập nhật:");
        System.out.println("   1. Tên khóa học");
        System.out.println("   2. Thời lượng khóa học");
        System.out.println("   3. Tên giảng viên");
        System.out.println("   4. Cả 3 trường thông tin trên");
        System.out.println("Nhập bất kì kí tự khác để hủy cập nhật");
        System.out.print("Lựa chọn của bạn: ");

        String choice = sc.nextLine();

        String name="";
        int duration=-1;
        String instructor="";

        System.out.println("Để trống bất kì thông tin nào để bỏ qua");
        switch(choice){
            case "1": //Nhập tên
                System.out.print("Nhập tên mới: ");
                name = sc.nextLine().trim();
                break;
            case "2": //Nhập thời lượng
                duration = CourseInput.inputDuration("Nhập thời lượng mới (đơn vị giờ): ");
                break;
            case "3": //Nhập giảng viên
                System.out.print("Nhập tên giảng viên mới: ");
                instructor = sc.nextLine().trim();
                break;
            case "4": //Nhập tên + thời lượng + giảng viên
                System.out.print("Nhập tên mới: ");
                name = sc.nextLine().trim();

                duration = CourseInput.inputDuration("Nhập thời lượng mới (đơn vị giờ): ");

                System.out.print("Nhập tên giảng viên mới: ");
                instructor = sc.nextLine().trim();

                break;

            default: return;
        }
        try{
            model.entity.Course course = new model.entity.Course(id,name,duration,instructor,null);
            courseService.updateInforCourse(course);
            System.out.println("Cập nhật thành công");
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    //4
    private static void deleteCourse(){
        int id = CourseInput.inputId("Nhập id khóa học cần xóa: ");
        if( courseService.checkCourseExist(id)==false){
            System.out.println("Không tìm thấy id cần xóa");
        }
        else {
            System.out.print("Xác nhận muốn xóa (Y/N): ");
            String choice = sc.nextLine().trim();
            if(choice.equalsIgnoreCase("y")) {
                try{
                    courseService.deleteCourse(id);
                    System.out.println("Đã xóa khóa học");
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            else System.out.println("Đã hủy xóa");
        }
    }

    //5
    private static void findCourse(){
        courseView.findCourse();
    }
    //6
    private static void sortCourses(){
        courseView.sortCourses();
    }
}
