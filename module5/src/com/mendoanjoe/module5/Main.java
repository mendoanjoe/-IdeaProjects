package com.mendoanjoe.module5;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static List<Data> dataList = new ArrayList<>();
    private static Connection connection;
    private static DefaultTableModel tableModel = new DefaultTableModel();
    private static MainActivity mainActivity = new MainActivity();
    private static int currentIndex;

    public static void main(String[] args) {
        currentIndex = -1;
        initView();
        getConnection();
        getData();

        mainActivity.getAddButton().addActionListener(e -> {
            if (mainActivity.getAddButton().getText().equals("Save")) {
                if (isValidate()) {
                    String query = "insert into mahasiswa (nama, alamat, telepon) VALUES (?,?,?)";
                    try {
                        PreparedStatement statement = connection.prepareStatement(query);
                        statement.setString(1, mainActivity.getTfNama().getText());
                        statement.setString(2, mainActivity.getTfAlamat().getText());
                        statement.setString(3, mainActivity.getTfTelepon().getText());
                        statement.executeUpdate();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    mainActivity.getTfNama().setText("");
                    mainActivity.getTfAlamat().setText("");
                    mainActivity.getTfTelepon().setText("");

                    setEnableText(false);

                }
            } else if (mainActivity.getAddButton().getText().equals("Add")) {
                mainActivity.getAddButton().setText("Save");
                setEnableText(true);
            } else {
                if (isValidate()) {
                    String query = "update mahasiswa set nama = ?, alamat = ?, telepon = ? where id = ?";
                    try {
                        PreparedStatement statement = connection.prepareStatement(query);
                        statement.setString(1, mainActivity.getTfNama().getText());
                        statement.setString(2, mainActivity.getTfAlamat().getText());
                        statement.setString(3, mainActivity.getTfTelepon().getText());
                        statement.setInt(4, dataList.get(currentIndex).getId());
                        statement.executeUpdate();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    mainActivity.getEditButton().setText("Edit");
                    mainActivity.getAddButton().setText("Add");

                    mainActivity.getTfNama().setText("");
                    mainActivity.getTfAlamat().setText("");
                    mainActivity.getTfTelepon().setText("");

                    setEnableText(false);
                }
            }
            getData();
        });

        mainActivity.getEditButton().addActionListener(e -> {
            if (mainActivity.getEditButton().getText().equals("Edit")) {
                mainActivity.getEditButton().setText("Cancel");
                mainActivity.getAddButton().setText("Update");
            } else {
                mainActivity.getEditButton().setText("Edit");
                mainActivity.getAddButton().setText("Add");
            }

            setEnableText(true);
        });

        mainActivity.getDeleteButton().addActionListener(e -> {
            if (currentIndex != -1) {
                String query = "delete from mahasiswa where id = ?";
                try {
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setInt(1, dataList.get(currentIndex).getId());
                    statement.executeUpdate();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                getData();
            }
        });

        mainActivity.getTable1().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int index = mainActivity.getTable1().getSelectedRow();
                currentIndex = index;
                if (index == -1) {
                    return;
                }

                String nama = String.valueOf(tableModel.getValueAt(index, 0));
                String alamat = String.valueOf(tableModel.getValueAt(index, 1));
                String telepon = String.valueOf(tableModel.getValueAt(index, 2));

                mainActivity.getTfNama().setText(nama);
                mainActivity.getTfAlamat().setText(alamat);
                mainActivity.getTfTelepon().setText(telepon);
            }
        });
    }

    private static void initView() {
        JFrame frame = new JFrame("Coba");
        frame.setContentPane(mainActivity.getPanel1());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        tableModel.addColumn("Nama");
        tableModel.addColumn("Alamat");
        tableModel.addColumn("Telepon");

        mainActivity.getTable1().setModel(tableModel);

        setEnableText(false);
    }


    private static void getConnection() {
        String user = "root";
        String pw = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String stringConnection = "jdbc:mysql://localhost:3306/db_mahasiswa?serverTimezone=UTC";
            connection = DriverManager.getConnection(stringConnection, user, pw);
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
    }

    private static void getData() {
        tableModel.getDataVector().removeAllElements();
        tableModel.fireTableDataChanged();
        String query = "select * from mahasiswa";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Data data = new Data(
                        resultSet.getInt("id"),
                        resultSet.getString("nama"),
                        resultSet.getString("alamat"),
                        resultSet.getString("telepon")
                );
                dataList.add(data);
                Object[] objects = {data.getNama(), data.getAlamat(), data.getPhone()};
                tableModel.addRow(objects);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static boolean isValidate() {
        return !mainActivity.getTfNama().getText().trim().isEmpty()
                && !mainActivity.getTfAlamat().getText().trim().isEmpty()
                && !mainActivity.getTfTelepon().getText().trim().isEmpty();
    }

    private static void setEnableText(boolean state) {
        mainActivity.getTfNama().setEnabled(state);
        mainActivity.getTfAlamat().setEnabled(state);
        mainActivity.getTfTelepon().setEnabled(state);
    }
}
