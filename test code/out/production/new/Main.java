
import DAO.LoginManagement;
import Menu.MenuAdmin;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        while(true){
            System.out.println("========HỆ THỐNG QUẢN LÝ ĐÀO TẠO========");
            System.out.println("1. Đăng nhập với tư cách quản trị viên\n"+
                                "2. Đăng nhập với tư cách học viên\n" +
                                "3. Thoát");
            System.out.println("========================================");
            System.out.print("Nhập lựa chọn: ");
            String choice = sc.nextLine();
            switch(choice){
                case "1":
                    if(!loginAsAdmin()) System.out.println("Hủy đăng nhập");
                    else {
                        System.out.println("Đăng nhập thành công");
                        MenuAdmin.MenuAdmin();
                    }
                    break;
                case "3":
                    System.out.println("Chào tạm biệt");
                    System.exit(0);
                default:
                    System.out.println("Vui lòng nhập số");
                    break;
            }
        }
    }
    public static boolean loginAsAdmin(){
        System.out.print("Đăng nhập với tư cách quản trị viên");
        while(true){
            System.out.println("(Để trống để thoát)");
            System.out.print("Vui lòng nhập username: ");
            String username = sc.nextLine().trim();
            if(username.toLowerCase().equals("")) return false;
            if(username.contains(" ")){
                System.out.println("Username không được chứa khoảng trắng");
                continue;
            }
            
            System.out.print("Vui lòng nhập password: ");
            String password = sc.nextLine().trim();
            if(password.toLowerCase().equals("")) return false;
            if(password.contains(" ")){
                System.out.println("Password không được chứa khoảng trắng");
                continue;
            }

            if(LoginManagement.loginAsAdmin(username,password)) return true;
            else {
                System.out.println("Username hoặc password không đúng");
            }
        }
    }
}
