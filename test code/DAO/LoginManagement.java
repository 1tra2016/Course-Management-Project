package DAO;
import java.sql.*;
import utils.DBConnection;
public class LoginManagement {
    public static boolean loginAsAdmin(String username, String password){
        String sql ="call login_as_admin(?,?,?)";
        try(Connection con = DBConnection.getConnection();
            CallableStatement cs = con.prepareCall(sql)
        ){
            cs.setString(1, username);
            cs.setString(2, password);
            cs.registerOutParameter(3, Types.BOOLEAN);

            cs.execute();
            return cs.getBoolean(3);
        }catch(Exception e){
            System.out.println(e);
            return false;
        }
    }

    public static boolean  login_as_student(String username, String password){
        String sql ="call login(?,?)";
        return false;
    }
}
