package server;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SimpleAuthService implements AuthService {

    private class UserData{
        String login;
        String password;
        String nickname;

        public UserData(String login, String password, String nickname) {
            this.login = login;
            this.password = password;
            this.nickname = nickname;
        }
    }

    private List<UserData> users;

    public SimpleAuthService() {
        try {
            Database.createUserData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getNicknameByLoginAndPassword(String login, String password) {
        String nickname;
        try {
            if (Database.getNicknameByLoginAndPassword(login, password) != null) {
                nickname = Database.getNicknameByLoginAndPassword(login, password);
                return nickname;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean registration(String login, String password, String nickname) {
        try {
            if (!Database.registration(login, password, nickname)){
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        for (UserData o : users ) {
//            if(o.login.equals(login)) {
//                return false;
//            }
//        }
//        users.add(new UserData(login, password, nickname));
        return true;
    }
}
