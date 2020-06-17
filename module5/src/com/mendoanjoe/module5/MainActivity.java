package com.mendoanjoe.module5;

import javax.swing.*;

public class MainActivity {
    private JPanel panel1;
    private JTextField tfNama;
    private JLabel namaLabel;
    private JTable table1;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JTextField tfAlamat;
    private JTextField tfTelepon;
    private JScrollPane mTable;

    public JPanel getPanel1() {
        return panel1;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JButton getEditButton() {
        return editButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JTextField getTfNama() {
        return tfNama;
    }

    public JTable getTable1() {
        return table1;
    }

    public JTextField getTfAlamat() {
        return tfAlamat;
    }

    public JTextField getTfTelepon() {
        return tfTelepon;
    }

}
