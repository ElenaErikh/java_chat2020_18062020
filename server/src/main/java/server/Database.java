package server;

import java.sql.*;

public class Database {
    private static Connection connection;
    private static Statement stmt;
    private static PreparedStatement psInsert;
    //private static PreparedStatement psChangeNick;

    public static void connect() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:main.db");
        stmt = connection.createStatement();
    }

    public static void disconnect() {
        try {
            stmt.close();
            connection.close();
            System.out.println("connection to bd is closed");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void prepareAllStatements() throws SQLException {
        //psChangeNick = connection.prepareStatement("SELECT ? FROM users");
    }

    public static void createUserData() throws SQLException {

        connection.setAutoCommit(false);

        for (int i = 1; i <= 10; i++) {
            psInsert.setString(1, "" + i);
            psInsert.setString(2, "" + i);
            psInsert.setString(3, "simple_nick" + i);
            psInsert.addBatch();
        }
        psInsert.executeBatch();
        connection.setAutoCommit(true);
    }

    public static String getNicknameByLoginAndPassword(String log, String pass) throws SQLException {
        String nickname;
        ResultSet rs = stmt.executeQuery("SELECT * FROM users");
        while (rs.next()) {
            if (rs.getString("login").equals(log) && rs.getString("password").equals(pass)) {
                nickname = rs.getString("nickname");
                return nickname;
            }
        }
        rs.close();
        return null;
    }

    public static boolean registration(String log, String pass, String nick) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT login FROM users");
        while (rs.next()) {
            if (rs.getString("login").equals(log)) {
                return false;
            }
        }
        psInsert.setString(1, log);
        psInsert.setString(2, pass);
        psInsert.setString(3, nick);
        psInsert.executeUpdate();

        return true;
    }

    public static void clearTable() throws SQLException {
        stmt.executeUpdate("DELETE FROM users;");
    }

//    public static void changeNickname(String nick) throws SQLException {
//        ResultSet rs = psChangeNick.executeQuery("SELECT nickname FROM users");
//        while (rs.next()) {
//            if (rs.getString("nickname").equals(nick)) {
//                psChangeNick.executeUpdate("UPDATE users SET nickname = nick ");
//            }else {
//                System.out.println("Ник, который вы хотите изменить, не найден!");
//                break;
//            }
//        }
//    }





}
