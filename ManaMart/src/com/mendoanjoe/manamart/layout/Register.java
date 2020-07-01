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
import java.util.Date;

public class Register {
    private JTextField registerTxtFieldNama;
    private JTextField registerTxtFieldUsername;
    private JPasswordField registerTxtFieldPassword;
    private JTextArea registerTxtFieldAlamat;
    private JTextField registerTxtFieldTglLahir;
    private JButton registerDaftarButton;
    private JButton registerMasukButton;
    private JPanel registerPanel;
    private JFrame frame;
    private Connection connection;

    public Register() {
        /**
         * Get Database Connection from Main
         */
        connection = Main.getDatabaseConnection();

        /**
         * Creating view
         */
        initFrame("Daftar Admin ManaMart");

        /**
         * Creating action
         * @onClicked, @onPressed, etc
         */
        initMasukButton();
        initDaftarButton();
    }

    private void initMasukButton() {
        registerMasukButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Main.route(Naming.TEXT_ROUTE_LOGIN, frame);
            }
        });
    }

    private void initDaftarButton() {
        registerDaftarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String nama = registerTxtFieldNama.getText();
                String username= registerTxtFieldUsername.getText();
                String password = String.valueOf(registerTxtFieldPassword.getPassword());
                String tanggal_lahir = registerTxtFieldTglLahir.getText();
                String alamat = registerTxtFieldAlamat.getText();

                if (!nama.isEmpty() && !username.isEmpty() && !password.isEmpty()
                        && !tanggal_lahir.isEmpty() && !alamat.isEmpty()) {
                    if (Helper.validDate(tanggal_lahir)) {
                        int userId = new RUser(connection).insertUser(nama, username, password, alamat, Helper.getDate(tanggal_lahir));
                        if (userId != -1) {
                            MUser user = new RUser(connection).selectOneUser(userId);
                            if (user != null) {
                                Helper.showDialog("Berhasil Membuat Akun");
                                Main.user = user;
                                Main.setUser(user);
                                Main.route(Naming.TEXT_ROUTE_MAIN, frame);
                            }
                        }
                    } else {
                        Helper.showDialog("Gunakan format tanggal dd/mm/yyyy");
                    }
                } else {
                    Helper.showDialog("Harap isi Semua Kolom");
                }
            }
        });
    }

    private void initFrame(String name) {
        frame = new JFrame(name);
        frame.setContentPane(registerPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
    }

    public void show() {
        frame.setVisible(true);
    }
}
