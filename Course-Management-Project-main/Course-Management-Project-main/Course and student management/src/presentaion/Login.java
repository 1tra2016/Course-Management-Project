package presentaion;

import business.impl.LoginServiceImpl;
import presentaion.admin.AdminView;
import presentaion.student.StudentView;

import java.util.Scanner;

public class Login {
    private static Scanner sc = new Scanner(System.in);
    private static LoginServiceImpl loginService = new LoginServiceImpl();

    public static void showMainMenu() {
        while (true) {
            System.out.println("========HỆ THỐNG QUẢN LÝ ĐÀO TẠO========");
            System.out.println("1. Đăng nhập với tư cách quản trị viên");
            System.out.println("2. Đăng nhập với tư cách học viên");
            System.out.println("3. Thoát");
            System.out.println("========================================");
            System.out.print("Nhập lựa chọn: ");

            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    if (login(true)==0) {
                        System.out.println("Đăng nhập thành công");
                        AdminView.MenuAdmin();
                    } else {
                        System.out.println("Hủy đăng nhập");
                    }
                    break;
                case "2":
                    int idStudent = login(false);
                    if (idStudent > 0) {
                        System.out.println("Đăng nhập thành công");
                        StudentView.MenuStudent(idStudent);
                    } else {
                        System.out.println("Hủy đăng nhập");
                    }
                    break;
                case "3":
                    System.out.println("Chào tạm biệt");
                    System.exit(0);
                default:
                    System.out.println("Vui lòng nhập số hợp lệ");
            }
        }
    }

    public static int login(boolean role) {
        System.out.println("Đăng nhập với tư cách "+(role?"admin":"student"));
        while (true) {
            System.out.println("(Để trống để thoát)");

            System.out.print((role?"Username":"Số điện thoại")+": ");
            String input = sc.nextLine().trim();
            if (input.isEmpty()) return -1;
            if (input.contains(" ")) {
                System.out.println((role?"Username":"Số điện thoại")+" không được chứa khoảng trắng");
                continue;
            }

            System.out.print("Password: ");
            String password = sc.nextLine().trim();
            if (password.isEmpty()) return -1;
            if (password.contains(" ")) {
                System.out.println("Password không được chứa khoảng trắng");
                continue;
            }
            try {
                return loginService.login(input, password,role);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}