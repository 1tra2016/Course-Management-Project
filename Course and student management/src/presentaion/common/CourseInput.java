package presentaion.common;

import business.ICourseService;
import business.impl.CourseServiceImpl;

import java.util.Scanner;

public class CourseInput {
    static Scanner sc = new Scanner(System.in);
    static final ICourseService courseService = new CourseServiceImpl();
    public static int inputDuration(String messsage){
        int duration;
        while(true){
            try{
                System.out.print(messsage);
                String input = sc.nextLine().trim();

                if(input.isEmpty())  return -1;

                duration = Integer.parseInt(input);
                if(duration<0) System.out.println("Thời lượng phải lớn hơn 0");

                break;
            } catch(NumberFormatException e) {
                System.out.println("Phải nhập số");
            } catch(IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }
        return duration;
    }

    public static int inputId(String messsage){
        int id;
        while(true){
            try{
                System.out.print(messsage);
                id = Integer.parseInt(sc.nextLine());
                if(courseService.checkCourseExist(id)==true)
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
