package com.mendoanjoe.manamart.layout;

import com.mendoanjoe.manamart.Main;
import com.mendoanjoe.manamart.Helper;
import com.mendoanjoe.manamart.Naming;
import com.mendoanjoe.manamart.model.MPreTransaction;
import com.mendoanjoe.manamart.model.MProduct;
import com.mendoanjoe.manamart.model.MUser;
import com.mendoanjoe.manamart.repostiory.RProduct;
import com.mendoanjoe.manamart.repostiory.RTransaction;
import com.mendoanjoe.manamart.repostiory.RTransactionItem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.Connection;
import java.util.ArrayList;

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
    private JPopupMenu popupMenu;
    private JFrame frame;

    private int globalTotal = 0;
    private int globalPembayaran = 0;
    private int globalKembalian = 0;

    private static DefaultTableModel tableModel = new DefaultTableModel();
    private ArrayList<MPreTransaction> mPreTransactions = new ArrayList<>();

    private Connection connection;

    public ManaMart(MUser user) {
        /**
         * Get Database Connection from Main
         */
        connection = Main.getDatabaseConnection();

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
        tableModel.addColumn(Naming.TEXT_TABLE_KODE_BARANG);
        tableModel.addColumn(Naming.TEXT_TABLE_NAMA);
        tableModel.addColumn(Naming.TEXT_TABLE_HARGA);
        tableModel.addColumn(Naming.TEXT_TABLE_JUMLAH);

        getTable().setModel(tableModel);
    }

    private void insertItems(MPreTransaction mPreTransaction) {
        boolean same = false;
        int indexSame = -1;

        /**
         * Check duplicated item
         */
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (mainTxtFieldKodeBarang.getText().equals(tableModel.getValueAt(i, 0))) {
                same = true;
                indexSame = i;
            }
        }

        if (!same) {
            /**
             * If not duplicated just insert
             */
            tableModel.addRow(new String[] {
                    mPreTransaction.getId(),
                    mPreTransaction.getName(),
                    mPreTransaction.getPrice(),
                    String.valueOf(mPreTransaction.getQty())
            });
            getTable().setModel(tableModel);
        } else if (indexSame != -1) {
            /**
             * If duplicated, update jumlah row using kodebarang
             */
            tableModel.setValueAt(String.valueOf(
                    mPreTransaction.getQty() + Integer.parseInt((String) tableModel.getValueAt(indexSame, 3))),
                    indexSame, 3
            );
            getTable().setModel(tableModel);
        }
    }

    private void deleteItem() {
        /**
         * Delete selected row from table
         */
        tableModel.removeRow(mainTable.getSelectedRow());
        getTable().setModel(tableModel);
    }

    private void clearItems() {
        /**
         * Delete all data on table
         */
        tableModel.setRowCount(0);
        getTable().setModel(tableModel);
    }

    private void initContextMenuTable() {
        /**
         * Creating table menu, if right click
         */
        popupMenu = new JPopupMenu();
        JMenuItem menuItemHapus = new JMenuItem("Hapus");
        JMenuItem menuItemHapusSemua = new JMenuItem("Hapus Semua");

        popupMenu.add(menuItemHapus);
        popupMenu.add(menuItemHapusSemua);

        menuItemHapusSemua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                clearItems();
            }
        });

        menuItemHapus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                deleteItem();
            }
        });
    }

    /**
     * Creating frame for application launch
     */
    private void initFrame(String name) {
        frame = new JFrame(name);
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
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
                            MProduct product = new RProduct(connection).selectOneProductByCode(codeProduct);
                            if (product != null) {
                                mainTxtFieldKodeBarang.setText(product.getCode());
                                mainTxtFieldNama.setText(product.getName());
                                mainTxtFieldHarga.setText(String.valueOf(product.getPrice()));

                                /**
                                 * Check textfield is not empty
                                 */
                                if (!mainTxtFieldNama.getText().isEmpty() && !mainTxtFieldHarga.getText().isEmpty()) {
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
                            String kodeBarang = mainTxtFieldKodeBarang.getText();
                            String nama = mainTxtFieldNama.getText();
                            String harga = mainTxtFieldHarga.getText();
                            int jumlah = Integer.parseInt(String.valueOf(mainTxtFieldJumlah.getText()));

                            /**
                             * Get data from tabel and input new data
                             */
                            insertItems(new MPreTransaction(kodeBarang, nama, harga, jumlah));

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
                    globalPembayaran = Integer.parseInt(mainTxtFieldPembayaran.getText());
                    globalKembalian = globalPembayaran - globalTotal;
                    mainTxtFieldKembalian.setText(Helper.convertToRupiah(globalKembalian));
                }
            }
        });
    }

    private void initTransaksiButton() {
        mainTransaksiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Main.route(Naming.TEXT_ROUTE_TRANSACTION, frame);
            }
        });
    }

    private void initProdukButton() {
        mainProdukButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Main.route(Naming.TEXT_ROUTE_PRODUCT, frame);
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
                Main.route(Naming.TEXT_ROUTE_LOGIN, frame);
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
                if (globalPembayaran > globalTotal) {
                    /**
                     * Save data table to database
                     */
                    int transactionId = new RTransaction(connection).insertTransaction(globalTotal, globalPembayaran, globalKembalian, 2);
                    if (transactionId != -1) {
                        for (int i = 0; i < tableModel.getRowCount(); i++) {
                            MProduct product = new RProduct(connection).selectOneProductByCode((String) tableModel.getValueAt(i, 0));
                            new RTransactionItem(connection).insertTransactionItem(String.valueOf(tableModel.getValueAt(i, 1)), Integer.parseInt((String) tableModel.getValueAt(i, 2)), Integer.parseInt((String) tableModel.getValueAt(i, 3)), transactionId, product.getId());
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
        clearItems();
    }

    private void getTotalFromTable() {
        int total = 0;

        for (int count = 0; count < mainTable.getRowCount(); count++) {
            int price = Integer.parseInt(String.valueOf((mainTable.getValueAt(count, 2))));
            int qty = Integer.parseInt(String.valueOf((mainTable.getValueAt(count, 3))));
            total += price * qty;
        }

        globalTotal = total;
        mainTxtFieldTotal.setText(String.valueOf(Helper.convertToRupiah(total)));
    }

    public void show() {
        frame.setVisible(true);
    }

    public JTable getTable() {
        mainTable.setComponentPopupMenu(popupMenu);
        return mainTable;
    }
}
