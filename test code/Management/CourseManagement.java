package Management;

import DAO.CourseDAO;
import entity.Course;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class CourseManagement {

    static Scanner sc =  new Scanner(System.in);
    public static void courseManagement(){
        while(true){
            System.out.println("========Chức năng quản lý khóa học========");
            System.out.println("1. Hiển thị danh sách khóa học\n"+
                                "2. Thêm mới khóa học\n" +
                                "3. Chỉnh sửa thông tin khóa học\n" +
                                "4. Xóa khóa học\n" +
                                "5. Tìm kiếm khóa học theo tên\n" +
                                "6. Sắp xếp khóa học theo tên hoặc thứ tự được thêm\n" +
                                "7. Quay về menu chính");
            System.out.print("Nhập lựa chọn: ");
            String choice = sc.nextLine();
            switch(choice){
                case "1":
                    showAllCourse();
                    break;
                case "2":
                    createNewCourse();
                    break;
                case "3":
                    updateInforCourse();
                    break;
                case "7":
                    return;
                default:
                    System.out.println("Vui lòng nhập số");
                    break;
            }
        }
    }
    public static void showAllCourse(){
        List<Course> courses = DAO.CourseDAO.getAllCourses();
        if(courses.isEmpty()){
            System.out.println("Danh sách trống");
            return;
        }
        System.out.println("===== DANH SÁCH KHÓA HỌC =====");
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
    public static void createNewCourse(){
        System.out.println("------Chức năng thêm khóa học mới------");
        String name;
        while(true){
            System.out.print("Nhập tên khóa học: ");
            name = sc.nextLine().trim();
            if(!name.isEmpty()) break;
            System.out.println("Tên khóa học không được để trống");
        }

        int duration;
        while(true){
            try{
                System.out.print("Nhập thời lượng khóa học (tính theo giờ): ");
                duration = Integer.parseInt(sc.nextLine());
                if(duration>0) break;
                System.out.println("Thời lượng phải lớn hơn 0");
            } catch(NumberFormatException e){
                System.out.println("Thời lượng khóa học phải là số");
            }
        }
        String  instructor;
        while(true){
            System.out.print("Nhập tên giảng viên: ");
            instructor = sc.nextLine();
            if(!instructor.isEmpty()) break;
            System.out.println("Tên giảng viên không được để trống");
        }

        if(CourseDAO.createNewCourse(name,duration,instructor)==true) System.out.println("Thêm khóa học thành công");
        else System.out.println("Đã xảy ra lỗi");
    }

    public static void updateInforCourse(){
        System.out.println("------Chức năng đổi thông tin khóa học------");
        int id;
        while(true){
            try{
                System.out.print("Nhập id khóa học muốn cập nhật: ");
                id = Integer.parseInt(sc.nextLine());
                if(CourseDAO.checkCourseExists(id)==true) break;
                else{
                    System.out.println("Khóa học có id "+id+" không tồn tại");
                    return;
                }
            } catch(NumberFormatException e){
                System.out.println("Phải nhập số");
            }
        }
        System.out.println("Chọn trường thông tin muốn cập nhật:\n" +
                "   1. Tên khóa học\n" +
                "   2. Thời lượng khóa học\n" +
                "   3. Tên giảng viên\n" +
                "   4. Cả 3 trường thông tin trên\n" +
                "Nhập bất kì kí tự khác để hủy cập nhật");
        System.out.print("Lựa chọn của bạn: ");
        String choice = sc.nextLine();
        String name="";
        int duration=-1;
        String instructor="";
        switch(choice){
            case "1":
                System.out.print("Nhập tên mới (hoặc để trống để hủy): ");
                name = sc.nextLine().trim();
                break;
            case "2":
                while(true){
                        try{
                        System.out.print("Nhập thời lượng mới (hoặc để trống để hủy): ");
                        String input = sc.nextLine().trim();
                        if(input.isEmpty()) break;
                        else duration = Integer.parseInt(input);

                        if(duration>0) break;
                        System.out.println("Thời lượng phải lớn hơn 0");
                    } catch(NumberFormatException e) {
                        System.out.println("Phải nhập số");
                    }
                }
                break;
            case "3":
                System.out.print("Nhập tên giảng viên mới (hoặc để trống để hủy): ");
                instructor = sc.nextLine().trim();
                break;
            case "4":
                System.out.print("Nhập tên mới (hoặc để trống để hủy): ");
                name = sc.nextLine();
                while(true){
                    try{
                        System.out.print("Nhập thời lượng mới (hoặc để trống để hủy): ");
                        String input = sc.nextLine().trim();
                        if(input.isEmpty()) break;
                        else duration = Integer.parseInt(input);

                        if(duration>0) break;
                        System.out.println("Thời lượng phải lớn hơn 0");
                    } catch(NumberFormatException e) {
                        System.out.println("Phải nhập số");
                    }
                }
                System.out.print("Nhập tên giảng viên mới: ");
                instructor = sc.nextLine();
                break;
            default: return;
        }

        try{
            CourseDAO.updateInforCourse(id,name,duration,instructor);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void deleteCourse(){
        int id;
        while(true){
            try{
                System.out.print("Nhập id khóa học cần xóa: ");
                id = Integer.parseInt(sc.nextLine());
                break;
            } catch(NumberFormatException e){
                System.out.println("Phải nhập số");
            }
        }
        if( CourseDAO.checkCourseExists(id)==false){
            System.out.println("Không tìm thấy id cần xóa");
            return;
        }
        else {
            System.out.print("Xác nhận muốn xóa (Y/N): ");
            String choice = sc.nextLine().trim();
            if(choice.equalsIgnoreCase("Y")) {
                CourseDAO.deleteCourse(id);
                System.out.println("Đã xóa khóa học");
            }
            else if(choice.equalsIgnoreCase("N")) return;
            else{
                System.out.println("Coi như bạn đã hủy xóa");
                return;
            }
        }
    }
}
