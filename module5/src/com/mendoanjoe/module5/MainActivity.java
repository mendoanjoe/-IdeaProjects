package com.mendoanjoe.module5;

import javax.swing.*;

public class MainActivity {
    private JPanel mainPanel;
    private JTable mainTable;
    private JLabel namaLabel;
    private JScrollPane mTable;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JTextField fieldNama;
    private JTextField fieldAlamat;
    private JTextField fieldTelepon;

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JButton getButtonAdd() {
        return addButton;
    }

    public JButton getButtonEdit() {
        return editButton;
    }

    public JButton getButtonDelete() {
        return deleteButton;
    }

    public JTextField getFieldNama() {
        return fieldNama;
    }

    public JTable getTable() {
        return mainTable;
    }

    public JTextField getFieldAlamat() {
        return fieldAlamat;
    }

    public JTextField getFieldTelepon() {
        return fieldTelepon;
    }

}
