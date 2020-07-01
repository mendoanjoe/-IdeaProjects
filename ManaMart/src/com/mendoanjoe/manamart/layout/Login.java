package com.mendoanjoe.manamart.layout;

import com.mendoanjoe.manamart.Helper;
import com.mendoanjoe.manamart.Main;
import com.mendoanjoe.manamart.Naming;
import com.mendoanjoe.manamart.model.MUser;
import com.mendoanjoe.manamart.repostiory.RUser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class Login {
    private JTextField loginTxtFieldUsername;
    private JButton loginDaftarButton;
    private JButton loginMasukButton;
    private JPasswordField loginTxtFieldPassword;
    private JPanel loginPanel;
    private JFrame frame;
    private Connection connection;

    public Login() {
        /**
         * Get Database Connection from Main
         */
        connection = Main.getDatabaseConnection();

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

    private void initDaftarButton() {
        loginDaftarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Main.route(Naming.TEXT_ROUTE_REGISTER, frame);
            }
        });
    }

    private void initMasukButton() {
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
                    MUser user = new RUser(connection).selectOneUserByUsername(username);
                    if (user != null) {
                        if (user.getPassword().equals(password)) {
                            Main.user = user;
                            Main.setUser(user);
                            Main.route(Naming.TEXT_ROUTE_MAIN, frame);
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

    private void initFrame(String name) {
        frame = new JFrame(name);
        frame.setContentPane(loginPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
    }

    public void show() {
        frame.setVisible(true);
    }
}
