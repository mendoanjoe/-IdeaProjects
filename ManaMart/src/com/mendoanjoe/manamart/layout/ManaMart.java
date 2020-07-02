package com.mendoanjoe.manamart.layout;

import com.mendoanjoe.manamart.Helper;
import com.mendoanjoe.manamart.Main;
import com.mendoanjoe.manamart.Naming;
import com.mendoanjoe.manamart.model.MPreTransaction;
import com.mendoanjoe.manamart.model.MProduct;
import com.mendoanjoe.manamart.model.MUser;
import com.mendoanjoe.manamart.repostiory.RProduct;
import com.mendoanjoe.manamart.repostiory.RTransaction;
import com.mendoanjoe.manamart.repostiory.RTransactionItem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;

public class ManaMart {
    private JButton mainKeluarButton;
    private JTextField mainTxtFieldKodeBarang;
    private JTextField mainTxtFieldNama;
    private JTextField mainTxtFieldHarga;
    private JTextField mainTxtFieldJumlah;
    private JTable mainTable;
    private JTextField mainTxtFieldTotal;
    private JTextField mainTxtFieldPembayaran;
    private JTextField mainTxtFieldKembalian;
    private JButton mainSelesaiButton;
    private JPanel mainPanel;
    private JLabel mainLabelCashier;
    private JButton mainTransaksiButton;
    private JButton mainProdukButton;
    private JPopupMenu mainPopUpMenu;
    private JFrame mainFrame;

    private int transactionTotal = 0;
    private int transactionPembayayaran = 0;
    private int transactionKembalian = 0;

    private DefaultTableModel mainTableModel = new DefaultTableModel();
    private Connection mainConnection;
    private MUser mUser;

    public ManaMart(MUser user) {
        /**
         * Get Database Connection from Main
         */
        mainConnection = Main.getDatabaseConnection();
        mUser = user;

        /**
         * Creating view
         */
        initFrame(Naming.TEXT_LAYOUT_MAIN);
        initContextMenuTable();
        initTable();

        /**
         * Admin detail
         */
        mainLabelCashier.setText(user.getName());

        /**
         * Creating action
         * @onClicked, @onPressed, etc
         */
        initKeluarButton();
        initSelesaiButton();
        initTransaksiButton();
        initProdukButton();
        initTextFieldJumlah();
        initTextFieldKodeBarang();
        initTextFieldPembayaran();
    }

    private void initTable() {
        /**
         * Create empty table with column
         */
        mainTableModel.addColumn(Naming.TEXT_TABLE_KODE_BARANG);
        mainTableModel.addColumn(Naming.TEXT_TABLE_NAMA);
        mainTableModel.addColumn(Naming.TEXT_TABLE_HARGA);
        mainTableModel.addColumn(Naming.TEXT_TABLE_JUMLAH);

        getTable().setModel(mainTableModel);
    }

    private void addTableItem(MPreTransaction mPreTransaction) {
        boolean itemDuplicated = false;
        int indexItemDuplicated = -1;

        /**
         * Check duplicated item
         */
        for (int i = 0; i < mainTableModel.getRowCount(); i++) {
            if (mainTxtFieldKodeBarang.getText().equals(mainTableModel.getValueAt(i, 0))) {
                itemDuplicated = true;
                indexItemDuplicated = i;
            }
        }

        if (!itemDuplicated) {
            /**
             * If not duplicated just insert
             */
            mainTableModel.addRow(new String[] {
                    mPreTransaction.getCode(),
                    mPreTransaction.getName(),
                    String.valueOf(mPreTransaction.getPrice()),
                    String.valueOf(mPreTransaction.getQty())
            });
            getTable().setModel(mainTableModel);
        } else if (indexItemDuplicated != -1) {
            /**
             * If duplicated, update jumlah row using kodebarang
             */
            mainTableModel.setValueAt(String.valueOf(
                    mPreTransaction.getQty() + Integer.parseInt((String) mainTableModel.getValueAt(indexItemDuplicated, 3))),
                    indexItemDuplicated, 3
            );
            getTable().setModel(mainTableModel);
        }
    }

    private void deleteTableItem() {
        /**
         * Delete selected row from table
         */
        mainTableModel.removeRow(mainTable.getSelectedRow());
        getTable().setModel(mainTableModel);
    }

    private void clearTableItem() {
        /**
         * Delete all data on table
         */
        mainTableModel.setRowCount(0);
        getTable().setModel(mainTableModel);
    }

    private void initContextMenuTable() {
        /**
         * Creating table menu, if right click
         */
        mainPopUpMenu = new JPopupMenu();
        JMenuItem menuItemHapus = new JMenuItem("Hapus");
        JMenuItem menuItemHapusSemua = new JMenuItem("Hapus Semua");

        mainPopUpMenu.add(menuItemHapus);
        mainPopUpMenu.add(menuItemHapusSemua);

        menuItemHapusSemua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                clearTableItem();
            }
        });

        menuItemHapus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                deleteTableItem();
            }
        });
    }

    /**
     * Creating mainFrame for application launch
     */
    private void initFrame(String name) {
        mainFrame = new JFrame(name);
        mainFrame.setContentPane(mainPanel);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();
    }

    private void initTextFieldKodeBarang() {
        /**
         * TextField KodeBarang
         * @OnKeyPressed
         */
        mainTxtFieldKodeBarang.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (!mainTxtFieldKodeBarang.getText().isEmpty()) {
                        try {
                            /**
                             * Load one data from db using parameter kodeBarang
                             */
                            String codeProduct = mainTxtFieldKodeBarang.getText().toUpperCase();
                            MProduct product = new RProduct(mainConnection).selectOneProductByCode(codeProduct);
                            if (product != null) {
                                mainTxtFieldKodeBarang.setText(product.getCode());
                                mainTxtFieldNama.setText(product.getName());
                                mainTxtFieldHarga.setText(String.valueOf(product.getPrice()));

                                /**
                                 * Check textfield is not empty
                                 */
                                String name = mainTxtFieldNama.getText();
                                String price = mainTxtFieldHarga.getText();
                                if (!name.isEmpty() && !price.isEmpty()) {
                                    mainTxtFieldJumlah.setEnabled(true);
                                    mainTxtFieldKodeBarang.setEnabled(false);
                                }
                            } else {
                                Helper.showDialog("Barang tidak ditemukan");
                            }
                        } catch (Exception e) {
                            clearDetailProccess();
                            Helper.showDialog("Harap masukan jumlah berupa angka");
                        }
                    }
                }
            }
        });
    }

    private void initTextFieldJumlah() {
        /**
         * TextField Jumlah
         * @OnKeyPressed
         */
        mainTxtFieldJumlah.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (!mainTxtFieldJumlah.getText().isEmpty()) {
                        try {
                            /**
                             * Get data from layout
                             */
                            String codeProduct = mainTxtFieldKodeBarang.getText();
                            String name = mainTxtFieldNama.getText();
                            int price = Integer.parseInt(String.valueOf(mainTxtFieldHarga.getText()));
                            int qty = Integer.parseInt(String.valueOf(mainTxtFieldJumlah.getText()));

                            /**
                             * Get data from tabel and input new data
                             */
                            addTableItem(new MPreTransaction(codeProduct, name, price, qty));

                            /**
                             * After add data
                             */
                            mainSelesaiButton.setEnabled(true);
                            mainTxtFieldPembayaran.setEnabled(true);
                            mainTxtFieldKodeBarang.requestFocus();
                            getTotalFromTable();
                            clearDetailProccess();
                        } catch (Exception e) {
                            mainTxtFieldJumlah.setText("");
                            Helper.showDialog("Harap masukan jumlah berupa angka" + e.getMessage());
                        }
                    }
                }
            }
        });
    }

    private void initTextFieldPembayaran() {
        /**
         * TextField Pembayaran
         * @OnKeyPressed
         */
        mainTxtFieldPembayaran.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent keyEvent) {
                if (!mainTxtFieldPembayaran.getText().isEmpty() && keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
                    transactionPembayayaran = Integer.parseInt(mainTxtFieldPembayaran.getText());
                    transactionKembalian = transactionPembayayaran - transactionTotal;
                    mainTxtFieldKembalian.setText(Helper.convertToRupiah(transactionKembalian));
                }
            }
        });
    }

    private void initTransaksiButton() {
        mainTransaksiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Main.route(Naming.TEXT_ROUTE_TRANSACTION, mainFrame);
            }
        });
    }

    private void initProdukButton() {
        mainProdukButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Main.route(Naming.TEXT_ROUTE_PRODUCT, mainFrame);
            }
        });
    }

    private void initKeluarButton() {
        /**
         * Button Keluar
         * @OnClicked
         */
        mainKeluarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Main.route(Naming.TEXT_ROUTE_LOGIN, mainFrame);
            }
        });
    }

    private void initSelesaiButton() {
        /**
         * Button Selesai
         * @OnClicked
         */
        mainSelesaiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (transactionPembayayaran >= transactionTotal) {
                    /**
                     * Save data table to database
                     */
                    int transactionId = new RTransaction(mainConnection).insertTransaction(transactionTotal, transactionPembayayaran, transactionKembalian, mUser.getId());
                    if (transactionId != -1) {
                        for (int i = 0; i < mainTableModel.getRowCount(); i++) {
                            MProduct product = new RProduct(mainConnection).selectOneProductByCode((String) mainTableModel.getValueAt(i, 0));
                            new RTransactionItem(mainConnection).insertTransactionItem(String.valueOf(mainTableModel.getValueAt(i, 1)), Integer.parseInt((String) mainTableModel.getValueAt(i, 2)), Integer.parseInt((String) mainTableModel.getValueAt(i, 3)), transactionId, product.getId());
                        }
                    }

                    clearTransactionProcess();
                    Helper.showDialog("Transaksi Berhasil");
                } else {
                    Helper.showDialog("Pembayaran Kurang");
                }
            }
        });
    }

    private void clearDetailProccess() {
        mainTxtFieldNama.setText("");
        mainTxtFieldHarga.setText("");
        mainTxtFieldJumlah.setText("");
        mainTxtFieldKodeBarang.setText("");

        mainTxtFieldJumlah.setEnabled(false);
        mainTxtFieldKodeBarang.setEnabled(true);
    }

    private void clearBillProcess() {
        mainTxtFieldPembayaran.setText("");
        mainTxtFieldKembalian.setText("");
        mainTxtFieldTotal.setText("");

        mainTxtFieldPembayaran.setEnabled(false);
        mainTxtFieldKembalian.setEnabled(false);
        mainTxtFieldTotal.setEnabled(false);
        mainSelesaiButton.setEnabled(false);
    }

    private void clearTransactionProcess() {
        clearDetailProccess();
        clearBillProcess();
        clearTableItem();
    }

    private void getTotalFromTable() {
        int totalTransaction = 0;

        for (int count = 0; count < mainTable.getRowCount(); count++) {
            int price = Integer.parseInt(String.valueOf((mainTable.getValueAt(count, 2))));
            int qty = Integer.parseInt(String.valueOf((mainTable.getValueAt(count, 3))));
            totalTransaction += price * qty;
        }

        transactionTotal = totalTransaction;
        mainTxtFieldTotal.setText(String.valueOf(Helper.convertToRupiah(totalTransaction)));
    }

    public void show() {
        mainFrame.setVisible(true);
    }

    private JTable getTable() {
        mainTable.setComponentPopupMenu(mainPopUpMenu);
        return mainTable;
    }
}
