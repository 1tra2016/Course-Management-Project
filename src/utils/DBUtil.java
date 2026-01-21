package utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {
    private static final String URL = "jdbc:postgresql://localhost:5432/course_management";
    private static final String USER = "postgres";
    private static final String PASSWORD = "12345";

    public static Connection getConnection() throws Exception {
        try{
            return DriverManager.getConnection(URL, USER, PASSWORD);
        }catch(Exception e){
            System.out.println("Kết nối database thất bại");
            return null;
        }
    }

    public static void closeConnection(Connection con) {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (Exception e) {
            System.out.println("Đóng connection thất bại");
        }
    }
}
