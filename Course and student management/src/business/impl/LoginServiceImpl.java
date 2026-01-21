package business.impl;

import business.ILoginService;
import dao.impl.LoginDAOImpl;

public class LoginServiceImpl implements ILoginService {
    LoginDAOImpl dao = new LoginDAOImpl();

    @Override
    public int login(String username, String password, boolean role) {

        if(role){
            if (!dao.loginAdmin(username, password)) {
                throw new IllegalArgumentException("Username hoặc password không đúng");
            }
            return 0;
        }
        else{
            int iStudent=dao.loginStudent(username, password);
            if (iStudent < 1 ) {
                throw new IllegalArgumentException("Số điện thoại hoặc password không đúng");
            }
            return iStudent;
        }
    }
}
