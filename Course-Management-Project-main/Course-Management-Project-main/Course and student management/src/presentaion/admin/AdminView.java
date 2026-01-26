package presentaion.admin;

import java.util.Scanner;

public class AdminView {
    static Scanner sc = new Scanner(System.in);
    public static void MenuAdmin(){
        while(true){
            System.out.println("========MENU ADMIN========");
            System.out.println("1. Quản lý khóa học\n"+
                                "2. Quản lý học viên\n" +
                                "3. Quản lý đăng ký học\n" +
                                "4. Thống kê học viên theo khóa học\n" +
                                "5. Đăng xuất");
            System.out.print("Nhập lựa chọn: ");
            String choice = sc.nextLine().trim();
            switch(choice){
                case "1":
                    CourseManagementView.courseManagement();
                    break;
                case "2":
                    StudentManagementView.studentManagement();
                    break;
                case "3": break;
                case "4": break;
                case "5":
                    System.out.println("Đã đăng xuất");
                    return;
                default:
                    System.out.println("Vui lòng nhập số");
                    break;
            }
        }
    }
}
