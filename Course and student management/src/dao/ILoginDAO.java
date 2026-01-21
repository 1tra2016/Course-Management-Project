package dao;

public interface ILoginDAO {
    boolean loginAdmin(String name, String password);
    int loginStudent(String name, String password);
}
