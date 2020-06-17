package com.mendoanjoe;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class FormKoneksi extends javax.swing.JFrame {
    private static Connection koneksi;
    private DefaultTableModel model;

    public FormKoneksi() {
        initComponents();
        model = new DefaultTableModel();
        this.jTable1.setModel(model);
        model.addColumn("ID");
        model.addColumn("Nama");
        model.addColumn("Alamat");
        model.addColumn("Telepon");
        ambil_data_tabel();
    }

    private static Connection buka_koneksi(){
        if(koneksi==null){
            try{
                String url = "jdbc:mysql://localhost:3306/belajar";
                String user = "root";
                String password = "";

                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                koneksi = DriverManager.getConnection(url,user,password);
            } catch (SQLException e) {
                System.out.println("Error membuat koneksi");
            }
        }
        return koneksi;
    }

    private void ambil_tabel_klik() {
        int i = this.jTable1.getSelectedRow();
        if (i == -1) {
            return;
        }
        String kode = (String) model.getValueAt(i, 0);
        this.lblKode.setText(kode);
        String nama = (String) model.getValueAt(i, 1);
        this.TxtNama.setText(nama);
        String alamat = (String) model.getValueAt(i, 2);
        this.TxtAlamat.setText(alamat);
        String telp = (String) model.getValueAt(i, 3);
        this.TxtTelepon.setText(telp);
    }

    private void ambil_data_tabel() {
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        try {
            Connection c = buka_koneksi();
            Statement s = c.createStatement();
            String sql = "SELECT * FROM anggota";
            ResultSet r = s.executeQuery(sql);

            while (r.next()) {
                Object[] o = new Object[4];
                o[0] = r.getString("id");
                o[1] = r.getString("nama");
                o[2] = r.getString("alamat");
                o[3] = r.getString("telp");
                model.addRow(o);
            }
            r.close();
            s.close();
            ambil_tabel_klik();
        } catch (SQLException e) {
            System.out.println("Terjadi kesalahan "+e.getMessage());
        }
    }
    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {
        this.ambil_tabel_klik();
    }
    private void cmbAddActionPerformed(java.awt.event.ActionEvent evt) {
        Connection c = buka_koneksi();
        if("Add".equals(this.cmbAdd.getText())) {
            this.cmbAdd.setText("Save");
            this.cmbEdit.setText("Cancel");
            this.cmbDelete.enable(false);
            this.lblKode.setText("");
            this.TxtNama.setText("");
            this.TxtAlamat.setText("");
            this.TxtTelepon.setText("");
        } else if("Save".equals(this.cmbAdd.getText())) {
            String sqlkode = "INSERT INTO anggota (nama, alamat, telp) values(?,?,?)";
            try {
                PreparedStatement p2 = c.prepareStatement(sqlkode);
                p2.setString(1, this.TxtNama.getText());
                p2.setString(2, this.TxtAlamat.getText());
                p2.setString(3, this.TxtTelepon.getText());
                p2.executeUpdate();
                p2.close();
                JOptionPane.showMessageDialog(this, "Data berhasil ditambahkan");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Terjadi kesalahan " +
                        ex.getMessage());
            }
            this.cmbAdd.setText("Add");
            this.cmbEdit.setText("Edit");
            this.cmbDelete.enable(true);
            this.cmbRefresh.enable(true);
        } else if("Update".equals(this.cmbAdd.getText())) {
            String sqlkode = "UPDATE anggota SET nama = ?, alamat = ?, telp = ? WHERE id = ?";
            try {
                PreparedStatement p2 = c.prepareStatement(sqlkode);
                p2.setString(1, this.TxtNama.getText());
                p2.setString(2, this.TxtAlamat.getText());
                p2.setString(3, this.TxtTelepon.getText());
                p2.setString(4, this.lblKode.getText());
                p2.executeUpdate();
                p2.close();
                JOptionPane.showMessageDialog(this, "Data berhasil diperbarui");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Terjadi kesalahan " +
                        ex.getMessage());
            }
            this.cmbAdd.setText("Add");
            this.cmbEdit.setText("Edit");
            this.cmbDelete.enable(true);
            this.cmbRefresh.enable(true);
        }
    }
    private void cmbEditActionPerformed(java.awt.event.ActionEvent evt) {
        if("Edit".equals(this.cmbEdit.getText())) {
            this.cmbAdd.setText("Update");
            this.cmbEdit.setText("Cancel");
            this.cmbDelete.enable(false);
            this.cmbRefresh.enable(false);
        } else if ("Cancel".equals(this.cmbEdit.getText())) {
            this.cmbAdd.setText("Add");
            this.cmbEdit.setText("Edit");
            this.cmbDelete.enable(true);
            this.cmbRefresh.enable(true);
        }
    }
    private void cmbDeleteActionPerformed(java.awt.event.ActionEvent evt) {
        Connection c = buka_koneksi();
        String sqlkode = "DELETE FROM anggota WHERE id = ?";
        try {
            PreparedStatement p2 = c.prepareStatement(sqlkode);
            p2.setString(1, this.lblKode.getText());
            p2.executeUpdate();
            p2.close();
            JOptionPane.showMessageDialog(this, "Data berhasil dihapus");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan " +
                    ex.getMessage());
        }
    }
    private void cmbRefreshActionPerformed(java.awt.event.ActionEvent evt) {
        ambil_data_tabel();
        this.lblKode.setText("");
        this.TxtNama.setText("");
        this.TxtAlamat.setText("");
        this.TxtTelepon.setText("");
    }
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info :
                    javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {

            java.util.logging.Logger.getLogger(FormKoneksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {

            java.util.logging.Logger.getLogger(FormKoneksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {

            java.util.logging.Logger.getLogger(FormKoneksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {

            java.util.logging.Logger.getLogger(FormKoneksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormKoneksi().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify
    private javax.swing.JTextField TxtAlamat;
    private javax.swing.JTextField TxtNama;
    private javax.swing.JTextField TxtTelepon;
    private javax.swing.JButton cmbAdd;
    private javax.swing.JButton cmbDelete;
    private javax.swing.JButton cmbEdit;
    private javax.swing.JButton cmbRefresh;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblKode;
    // End of variables declaration

}