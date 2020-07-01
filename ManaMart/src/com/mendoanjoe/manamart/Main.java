package com.mendoanjoe.manamart;

import com.mendoanjoe.manamart.layout.*;
import com.mendoanjoe.manamart.model.MUser;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class Main {
    // Layout
    private static ManaMart mainActivity;
    private static Login loginActivity;
    private static Register registerActivity;
    private static Transaction transactionActivity;
    private static Product productActivity;

    // User
    public static MUser user;

    public static void main(String[] args) {
        initUser();
    }

    public static void initUser() {
        if (Main.getUser() == null) {
            loginActivity = new Login();
            loginActivity.show();
        }
    }

    public static void route(String name, JFrame frame) {
        switch (name) {
            case Naming.TEXT_ROUTE_MAIN: {
                frame.setVisible(false);
                mainActivity = new ManaMart(Main.getUser());
                mainActivity.show();
                break;
            }
            case Naming.TEXT_ROUTE_REGISTER: {
                frame.setVisible(false);
                registerActivity = new Register();
                registerActivity.show();
                break;
            }
            case Naming.TEXT_ROUTE_LOGIN: {
                frame.setVisible(false);
                loginActivity.show();
                break;
            }
            case Naming.TEXT_ROUTE_TRANSACTION: {
                frame.setVisible(false);
                transactionActivity = new Transaction();
                transactionActivity.show();
                break;
            }
            case Naming.TEXT_ROUTE_PRODUCT: {
                frame.setVisible(false);
                productActivity = new Product();
                productActivity.show();
                break;
            }
        }
    }

    public static Connection getDatabaseConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String stringConnection = "jdbc:mysql://localhost:3306/" + Naming.DATABASE_NAME + "?serverTimezone=UTC"; // fix error time zone
            connection = DriverManager.getConnection(stringConnection, Naming.DATABASE_USERNAME, Naming.DATABASE_PASSWORD);
        } catch (Exception e) {
            Helper.showDialog("Koneksi Database Error: " + e.getMessage());
        }

        return connection;
    }

    public static MUser getUser() {
        return user;
    }

    public static void setUser(MUser user) {
        Main.user = user;
    }
}
