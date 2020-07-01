package com.mendoanjoe.manamart.layout;

import com.mendoanjoe.manamart.Helper;
import com.mendoanjoe.manamart.Main;
import com.mendoanjoe.manamart.Naming;
import com.mendoanjoe.manamart.model.MProduct;
import com.mendoanjoe.manamart.model.MTransaction;
import com.mendoanjoe.manamart.model.MTransactionItem;
import com.mendoanjoe.manamart.model.MUser;
import com.mendoanjoe.manamart.repostiory.RProduct;
import com.mendoanjoe.manamart.repostiory.RTransaction;
import com.mendoanjoe.manamart.repostiory.RTransactionItem;
import com.mendoanjoe.manamart.repostiory.RUser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.Connection;
import java.util.List;

public class Product {
    private JTextField productTxtFieldId;
    private JTextField productTxtFieldKode;
    private JTextField productTxtFieldNama;
    private JTextField productTxtFieldHarga;
    private JTable productTable;
    private JButton productTambahButton;
    private JButton productHapusButton;
    private JButton productUbahButton;
    private JPanel productPanel;

    private JFrame frame;
    private Connection connection;

    private static DefaultTableModel productTableModel = new DefaultTableModel();

    public Product() {
        /**
         * Get Database Connection from Main
         */
        connection = Main.getDatabaseConnection();

        /**
         * Creating view
         */
        initFrame("Data Produk");
        initProductTable();

        /**
         * Creating action
         * @onClicked, @onPressed, etc
         */
        initTambahButton();
        initHapusButton();
        initUbahButton();
    }

    private void initFrame(String name) {
        frame = new JFrame(name);
        frame.setContentPane(productPanel);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                Main.route(Naming.TEXT_ROUTE_MAIN, frame);
            }
        });
        frame.pack();
    }

    public JTable getProductTable() {
        return productTable;
    }

    public void show() {
        frame.setVisible(true);
    }

    private void clearProductTable() {
        /**
         * Delete all data on table
         */
        productTableModel.setRowCount(0);
        getProductTable().setModel(productTableModel);
    }

    private void initProductTable() {
        /**
         * Create empty table with column
         */
        productTableModel.addColumn("Id");
        productTableModel.addColumn("Kode");
        productTableModel.addColumn("Nama");
        productTableModel.addColumn("Harga");

        loadDataTable();
        getProductTable().setModel(productTableModel);

        productTable.setCellSelectionEnabled(true);
        productTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if (productTable.getSelectedRow() != -1) {
                    int selectedRow = productTable.getSelectedRow();
                    productTxtFieldId.setText(String.valueOf(productTable.getValueAt(selectedRow, 0)));
                    productTxtFieldKode.setText(String.valueOf(productTable.getValueAt(selectedRow, 1)));
                    productTxtFieldNama.setText(String.valueOf(productTable.getValueAt(selectedRow, 2)));
                    productTxtFieldHarga.setText(String.valueOf(productTable.getValueAt(selectedRow, 3)));

                    productUbahButton.setEnabled(true);
                    productHapusButton.setEnabled(true);
                    productTambahButton.setText("Bersihkan");
                }
            }
        });
    }

    private void loadDataTable() {
        clearProductTable();
        List<MProduct> products = new RProduct(connection).selectAllProduct();

        for (MProduct product: products) {
            productTableModel.addRow(new String[] {
                    String.valueOf(product.getId()),
                    product.getCode(),
                    product.getName(),
                    String.valueOf(product.getPrice()),
            });
        }

        getProductTable().setModel(productTableModel);
    }

    private void clearTextField() {
        productTxtFieldHarga.setText("");
        productTxtFieldId.setText("0");
        productTxtFieldKode.setText("");
        productTxtFieldNama.setText("");

        productTambahButton.setText("Tambah");
        productHapusButton.setEnabled(false);
        productUbahButton.setEnabled(false);
    }

    private void initTambahButton() {
        productTambahButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String code = productTxtFieldKode.getText();
                String name = productTxtFieldNama.getText();
                String price = productTxtFieldHarga.getText();

                if (!code.isEmpty() && !name.isEmpty() && !price.isEmpty() && !productTambahButton.getText().equals("Bersihkan")) {
                    int product = new RProduct(connection).insertProduct(code, name, Integer.parseInt(price));
                    if (product != -1) {
                        clearTextField();
                        loadDataTable();
                        Helper.showDialog("Berhasil menambahkan produk");
                    }
                } else {
                    clearTextField();
                }
            }
        });
    }

    private void initHapusButton() {
        productHapusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (productTambahButton.getText().equals("Bersihkan")) {
                    int productId = Integer.parseInt(productTxtFieldId.getText());
                    boolean product = new RProduct(connection).deleteProduct(productId);

                    if (product) {
                        clearTextField();
                        loadDataTable();
                        Helper.showDialog("Berhasil menghapus produk");
                    }
                }
            }
        });
    }

    private void initUbahButton() {
        productUbahButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String code = productTxtFieldKode.getText();
                String name = productTxtFieldNama.getText();
                String price = productTxtFieldHarga.getText();

                if (!code.isEmpty() && !name.isEmpty() && !price.isEmpty() && productTambahButton.getText().equals("Bersihkan")) {
                    int productId = Integer.parseInt(productTxtFieldId.getText());
                    boolean product = new RProduct(connection).updateProduct(code, name, Integer.parseInt(price), productId);

                    if (product) {
                        clearTextField();
                        loadDataTable();
                        Helper.showDialog("Berhasil mengubah produk");
                    }
                }
            }
        });
    }
}
