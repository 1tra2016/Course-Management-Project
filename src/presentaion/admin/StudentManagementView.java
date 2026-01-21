package presentaion.admin;

import business.impl.StudentManagementServiceImpl;
import model.entity.Student;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class StudentManagementView {
    static StudentManagementServiceImpl studentService = new StudentManagementServiceImpl();
    static Scanner sc = new Scanner(System.in);
    public static void studentManagement(){
        while(true){
            System.out.println("========Chức năng quản lý học viên========");
            System.out.println("1. Hiển thị danh sách học viên");
            System.out.println("2. Thêm mới học viên" );
            System.out.println("3. Chỉnh sửa thông tin học viên" );
            System.out.println("4. Xóa học viên" );
            System.out.println("5. Tìm kiếm học viên theo tên, email hoặc id" );
            System.out.println("6. Sắp xếp học viên theo tên hoặc id" );
            System.out.println("7. Quay về menu chính");
            System.out.print("Nhập lựa chọn: ");
            String choice = sc.nextLine();
            switch(choice){
                case "1":
                    showAllStudents();
                    break;
                case "2":
                    createNewStudent();
                    break;
                case "3":
                    updateInforStudent();
                    break;
                case "4":
                    deleteStudent();
                    break;
                case "5":
                    findStudent();
                    break;
                case "6":
                    sortStudents();
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
    public static void showAllStudents(){
        try {
            List<Student> student = studentService.showAllStudents();
            if (student.isEmpty()) {
                System.out.println("Không tìm thấy học viên nào");
            } else {
                showStudents(student);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //2
    public static void createNewStudent(){
        System.out.println("------Chức năng thêm học viên mới------");
        String name;
        while(true){
            System.out.print("Nhập tên học viên: ");
            name = sc.nextLine().trim();
            if(!name.isEmpty()) break;
            System.out.println("Tên học viên không được để trống");
        }

        LocalDate dob = inputdob("Nhập ngày sinh học viên (YYYY-MM-DD) : ");
        String email = inputEmail("Nhập email học viên: ");
        Boolean sex = inputSex("Nhập giới tính (Male/Female) hoặc (M/F): ");
        String phone = inputPhone("Nhập số điện thoại học viên: ");

        try{
            Student student = new Student(0,name, dob, email, sex, phone);
            student.setName(name);
            student.setDob(dob);
            student.setEmail(email);
            student.setSex(sex);
            student.setPhone(phone);

            studentService.createNewStudent(student);
            System.out.println("Thêm học viên thành công");
        } catch(Exception e){
            e.printStackTrace();
        }

    }

    //3
    public static void updateInforStudent(){
        System.out.println("------Chức năng đổi thông tin học viên------");

        int id = inputId("Nhập id học viên muốn cập nhật: ");

        System.out.println("Chọn trường thông tin muốn cập nhật:");
        System.out.println("   1. Tên học viên");
        System.out.println("   2. Ngày sinh học viên");
        System.out.println("   3. Email học viên");
        System.out.println("   4. Giới tính học viên");
        System.out.println("   5. Số điện thoại học viên");
        System.out.println("   6. Nhập nhiều trường thông tin trên");
        System.out.println("   7. Đặt lại mật khẩu mặc định");
        System.out.println("Nhập bất kì kí tự khác để hủy cập nhật");
        System.out.print("Lựa chọn của bạn: ");

        String choice = sc.nextLine();
        String name =null;
        LocalDate dob =null;
        String email=null;
        Boolean sex= null;
        String phone=null;

        System.out.println("Để trống bất kì thông tin nào để giữ nguyên thông tin cũ");
        switch(choice){
            case "1": //Nhập tên
                System.out.print("Nhập tên mới: ");
                name = sc.nextLine().trim();
                break;
            case "2":
                dob = inputdob("Nhập ngày sinh mới (YYYY-MM-DD): ");
                break;
            case "3":
                email = inputEmail("Nhập email mới: ");
                break;
            case "4":
                sex = inputSex("Nhập giới tính mới: ");
                break;
            case "5":
                phone = inputPhone("Nhập số điện thoại mới: ");
                break;
            case "6":
                System.out.print("Nhập tên mới: ");
                name = sc.nextLine().trim();
                dob = inputdob("Nhập ngày sinh mới (YYYY-MM-DD): ");
                email = inputEmail("Nhập email mới: ");
                sex = inputSex("Nhập giới tính mới: ");
                phone = inputPhone("Nhập số điện thoại mới: ");
                break;
            case "7":
                studentService.resetPasswordStudent(id);
                return;
            default: return;
        }
        try{
            Student course = new Student(id,name,dob,email,sex,phone);
            studentService.updateInforStudent(course);
            System.out.println("Cập nhật thành công");
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    //4
    public static void findStudent() {
        System.out.print("Nhập từ khóa tìm kiếm (bỏ trống để hủy): ");
        String keyword = sc.nextLine().trim();
        if(keyword.isEmpty())  return;

        try {
            List<Student> student = studentService.findStudent(keyword);
            if (student.isEmpty()) {
                System.out.println("Không tìm thấy học viên nào");
            } else {
                showStudents(student);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //5
    public static void deleteStudent(){
        int id = inputId("Nhập id học viên cần xóa: ");
        if( studentService.checkStudentExist(id)==false){
            System.out.println("Không tìm thấy id cần xóa");
        }
        else {
            System.out.print("Xác nhận muốn xóa (Y/N): ");
            String choice = sc.nextLine().trim();
            if(choice.equalsIgnoreCase("y")) {
                try{
                    studentService.deleteStudent(id);
                    System.out.println("Đã xóa học viên");
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            else System.out.println("Đã hủy xóa");
        }
    }

    //6
    public static void sortStudents(){
        boolean idOrName;
        while(true){
            try{
                System.out.println("Sắp xếp theo: ");
                System.out.println("    1. Id học viên");
                System.out.println("    2. Tên học viên");
                System.out.println("    3. Thoát");
                System.out.print("Lựa chọn của bạn: ");
                String input = sc.nextLine().trim();
                if( input.equals("3") ) {
                    return;
                }
                else if(input.equals("1")) {
                    idOrName =true;
                    break;
                }
                else if(input.equals("2")) {
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
                System.out.println("    1. Từ dưới lên trên");
                System.out.println("    2. Từ trên xuống dưới");
                System.out.println("    3. Thoát");
                System.out.print("Lựa chọn của bạn: ");
                String input  = sc.nextLine().trim();
                if(input.equals("3")) {
                    return;
                }
                else if(input.equals("1")) {
                    orderBy =true;
                    break;
                }
                else if(input.equals("2")) {
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

        List<Student> student = studentService.sortListStudent(studentService.showAllStudents(),idOrName,orderBy);
        showStudents(student);
    }

    //biểu diễn
    private static void showStudents(List<Student> student){
        if (student == null || student.isEmpty()) {
            System.out.println("Không có học viên nào");
            return;
        }
        System.out.println("------DANH SÁCH HỌC VIÊN------");
        System.out.printf("%-10s %-20s %-15s %-20s %-10s %-15s %-20s%n",
                "Id","Tên học viên", "Ngày sinh", "Email", "Giới tính","Số điện thoại","Ngày tạo");

        for (Student s : student) {
            System.out.printf("%-10s %-20s %-15s %-20s %-10s %-15s %-20s%n",
                    s.getId(),
                    s.getName(),
                    s.getDob(),
                    s.getEmail(),
                    s.getSex() ? "Nam":"Nữ",
                    s.getPhone(),
                    s.getCreatedAt().toLocalDateTime().toLocalDate()
            );
        }
    }

    //kiểm soát nhập
    private static LocalDate inputdob(String messsage){
        LocalDate dob=null;
        while(true){
            try{
                System.out.print(messsage);
                String input = sc.nextLine().trim();

                if(input.isEmpty())  return null;

                dob = LocalDate.parse(input);
                break;
            } catch(DateTimeParseException e) {
                System.out.println("Phải nhập đúng định dạng: 'YYYY-MM-DD'");
            } catch(IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }
        return dob;
    }

    //kiểm soát nhập
    private static String inputEmail(String messsage){
        String email = null;
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        while(true){
            System.out.print(messsage);
            email = sc.nextLine().trim();

            if(email.isEmpty())  return null;

            if(Pattern.matches(regex,email)) return email;

            System.out.println("Email không hợp lệ");
        }
    }

    //kiểm soát nhập
    private static Boolean inputSex(String messsage){
        while(true){
            System.out.print(messsage);
            String input = sc.nextLine().trim();
            if(input.isEmpty()) return null;
            else if(input.equals("Male") || input.equals("M")) return true;
            else if(input.equals("Female") || input.equals("F")) return false;
            else System.out.println("Lựa chọn không tồn tại");
        }
    }

    //Kiểm soát nhập
    private static String inputPhone(String messsage){
        String phone = null;
        String regex = "^(\\+84|84|0)(3|5|7|8|9)[0-9]{8}$";
        while(true){
            System.out.print(messsage);
            phone = sc.nextLine().trim();

            if(phone.isEmpty())  return null;

            if(Pattern.matches(regex,phone)) return phone;

            System.out.println("Số điện thoại không hợp lệ");
        }
    }

    //kiểm soát nhập
    public static int inputId(String messsage){
        int id;
        while(true){
            try{
                System.out.print(messsage);
                id = Integer.parseInt(sc.nextLine());
                if(studentService.checkStudentExist(id)==true)
                    break;

            } catch(NumberFormatException e ){
                System.out.println("Phải nhập số");

            } catch(IllegalStateException e){
                System.out.println(e.getMessage());
            }
        }
        return id;
    }
}
