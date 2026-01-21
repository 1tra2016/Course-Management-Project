package dao.impl;

import dao.ILoginDAO;
import utils.DBUtil;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

public class LoginDAOImpl implements ILoginDAO {

    public boolean loginAdmin(String username, String password){
        String sql ="call login_as_admin(?,?,?)";
        Connection con = null;
        try{
            con = DBUtil.getConnection();
            CallableStatement call = con.prepareCall(sql);

            call.setString(1, username);
            call.setString(2, password);
            call.registerOutParameter(3, Types.BOOLEAN);

            call.execute();
            return call.getBoolean(3);
        }catch(Exception e){
            System.out.println(e);
            return false;
        }
    }

    public int loginStudent(String phone, String password){
        String sql ="call login_as_student(?,?,?)";
        Connection con = null;
        try{
            con = DBUtil.getConnection();
            CallableStatement call = con.prepareCall(sql);

            call.setString(1, phone);
            call.setString(2, password);
            call.registerOutParameter(3, Types.INTEGER);

            call.execute();
            return call.getInt(3);
        }catch(Exception e){
            System.out.println(e);
            return -1;
        }
    }
}
