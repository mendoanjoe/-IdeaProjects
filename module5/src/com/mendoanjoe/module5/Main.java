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
    private static DefaultTableModel tableModel = new DefaultTableModel();
    private static MainActivity mainActivity = new MainActivity();
    private static int currentIndex = -1;

    /**
     * main sebagai tempat fungsi di panggil
     */
    public static void main(String[] args) {
        // membuat tampilan gui
        initView();

        /**
         * - membuat koneksi ke database
         * - mengambil data mahasiswa dari database
         * - menampilkan pada table di main activity
         */
        Query query = new Query(getConnection());
        getDataMahasiswa(query);

        /**
         * apabila tombol add di tekan
         * terdapat 2 kondisi
         * - tombol add dengan text 'save'
         *   maka akan menyimpan data ke table mahasiswa
         * - tombol add dengan text 'add'
         *   maka akan merubah text dari 'add' ke 'save'
         * - tombol add dengan text 'update'
         *   maka akan mengupdate data ke table mahasiswa berdasarkan index
         *   yang di ambil dari selected row pada tabel main activity
         */
        mainActivity.getButtonAdd().addActionListener(e -> {
            if (mainActivity.getButtonAdd().getText().equals("Save")) {
                if (isValidate()) {
                    // memanggil fungsi insert
                    query.insertMahasiswa(
                            mainActivity.getFieldNama().getText(),
                            mainActivity.getFieldAlamat().getText(),
                            mainActivity.getFieldTelepon().getText());

                    clearTextField();
                }
            } else if (mainActivity.getButtonAdd().getText().equals("Add")) {
                mainActivity.getButtonAdd().setText("Save");
                setEnableText(true);
            } else {
                if (isValidate()) {
                    // memanggil fungsi update
                    query.updateMahasiswa(
                            mainActivity.getFieldNama().getText(),
                            mainActivity.getFieldAlamat().getText(),
                            mainActivity.getFieldTelepon().getText(), currentIndex);

                    mainActivity.getButtonEdit().setText("Edit");
                    mainActivity.getButtonAdd().setText("Add");

                    clearTextField();
                }
            }
            // mengupdate tampilan dengan data terbaru
            getDataMahasiswa(query);
        });

        mainActivity.getButtonEdit().addActionListener(e -> {
            if (mainActivity.getButtonEdit().getText().equals("Edit")) {
                mainActivity.getButtonEdit().setText("Cancel");
                mainActivity.getButtonAdd().setText("Update");
            } else {
                mainActivity.getButtonEdit().setText("Edit");
                mainActivity.getButtonAdd().setText("Add");
            }

            setEnableText(true);
        });

        /**
         * ketika tombol delete di tekan maka
         * akan memanggil query delete berdasarkan selected row pada table main activity
         */
        mainActivity.getButtonDelete().addActionListener(e -> {
            if (currentIndex != -1) {
                query.deleteMahasiswa(currentIndex);
                getDataMahasiswa(query);
            }
        });

        /**
         * untuk mengambil selected row dan menampilkan selected row
         * ke text field yang ada
         */
        mainActivity.getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int index = mainActivity.getTable().getSelectedRow();
                currentIndex = index;
                if (index == -1) {
                    return;
                }

                String nama = String.valueOf(tableModel.getValueAt(index, 0));
                String alamat = String.valueOf(tableModel.getValueAt(index, 1));
                String telepon = String.valueOf(tableModel.getValueAt(index, 2));

                mainActivity.getFieldNama().setText(nama);
                mainActivity.getFieldAlamat().setText(alamat);
                mainActivity.getFieldTelepon().setText(telepon);
            }
        });
    }

    private static void clearTextField() {
        mainActivity.getFieldNama().setText("");
        mainActivity.getFieldAlamat().setText("");
        mainActivity.getFieldTelepon().setText("");

        setEnableText(false);
    }

    private static void initView() {
        JFrame frame = new JFrame("Coba");
        frame.setContentPane(mainActivity.getMainPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        tableModel.addColumn("Nama");
        tableModel.addColumn("Alamat");
        tableModel.addColumn("Telepon");

        mainActivity.getTable().setModel(tableModel);

        setEnableText(false);
    }


    private static Connection getConnection() {
        Connection connection = null;
        String user = "root";
        String pw = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String stringConnection = "jdbc:mysql://localhost:3306/db_mahasiswa?serverTimezone=UTC"; // fix error time zone
            connection = DriverManager.getConnection(stringConnection, user, pw);
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
        
        return connection;
    }

    private static void getDataMahasiswa(Query query) {
        tableModel.getDataVector().removeAllElements(); // menghapus semua data pada table main activity
        tableModel.fireTableDataChanged();
        dataList = query.selectAllMahasiswa(); // memanggil query select mahasiswa

        for (int i = 0; i < dataList.size(); i++) {
            Object[] objects = {dataList.get(i).getNama(), dataList.get(i).getAlamat(), dataList.get(i).getPhone()};
            tableModel.addRow(objects);
        }
    }

    private static boolean isValidate() {
        return !mainActivity.getFieldNama().getText().trim().isEmpty()
                && !mainActivity.getFieldAlamat().getText().trim().isEmpty()
                && !mainActivity.getFieldTelepon().getText().trim().isEmpty();
    }

    private static void setEnableText(boolean state) {
        mainActivity.getFieldNama().setEnabled(state);
        mainActivity.getFieldAlamat().setEnabled(state);
        mainActivity.getFieldTelepon().setEnabled(state);
    }
}
