package com.mendoanjoe.manamart.layout;

import com.mendoanjoe.manamart.Helper;
import com.mendoanjoe.manamart.Main;
import com.mendoanjoe.manamart.Naming;
import com.mendoanjoe.manamart.intrf.LoginMetode;
import com.mendoanjoe.manamart.model.MUser;
import com.mendoanjoe.manamart.repostiory.RUser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class Login implements LoginMetode {
    private JTextField loginTxtFieldUsername;
    private JButton loginDaftarButton;
    private JButton loginMasukButton;
    private JPasswordField loginTxtFieldPassword;
    private JPanel loginPanel;
    private JFrame loginFrame;
    private Connection loginConnection;

    public Login() {
        /**
         * Get Database Connection from Main
         */
        loginConnection = Main.getDatabaseConnection();

        /**
         * Creating view
         */
        initFrame("Masuk Admin ManaMart");

        /**
         * Creating action
         * @onClicked, @onPressed, etc
         */
        initMasukButton();
        initDaftarButton();
    }



    public void show() {
        loginFrame.setVisible(true);
    }

    @Override
    public void initDaftarButton() {
        loginDaftarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Main.route(Naming.TEXT_ROUTE_REGISTER, loginFrame);
            }
        });
    }

    @Override
    public void initMasukButton() {
        /**
         * Button Masuk
         * @OnClicked
         */
        loginMasukButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String username = loginTxtFieldUsername.getText();
                String password = String.valueOf(loginTxtFieldPassword.getPassword());

                if (!username.isEmpty() && !password.isEmpty()) {
                    MUser user = new RUser(loginConnection).selectOneUserByUsername(username);
                    if (user != null) {
                        if (user.getPassword().equals(password)) {
                            Main.user = user;
                            Main.setUser(user);
                            Main.route(Naming.TEXT_ROUTE_MAIN, loginFrame);
                        }
                    } else {
                        Helper.showDialog("Username / Password Salah");
                    }
                } else {
                    Helper.showDialog("Harap isi Username & Password");
                }
            }
        });
    }

    @Override
    public void initFrame(String name) {
        loginFrame = new JFrame(name);
        loginFrame.setContentPane(loginPanel);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.pack();
    }
}
