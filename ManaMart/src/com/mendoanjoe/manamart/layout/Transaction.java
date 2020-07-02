package com.mendoanjoe.manamart.layout;

import com.mendoanjoe.manamart.Helper;
import com.mendoanjoe.manamart.Main;
import com.mendoanjoe.manamart.Naming;
import com.mendoanjoe.manamart.model.MTransaction;
import com.mendoanjoe.manamart.model.MTransactionItem;
import com.mendoanjoe.manamart.model.MUser;
import com.mendoanjoe.manamart.repostiory.RTransaction;
import com.mendoanjoe.manamart.repostiory.RTransactionItem;
import com.mendoanjoe.manamart.repostiory.RUser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.util.List;

public class Transaction {
    private JTextField transactionCariTextField;
    private JTable transactionTable;
    private JPanel transactionPanel;
    private JButton cariButton;
    private JFrame transactionFrame;
    private Connection transactionConnection;
    private DefaultTableModel transactionTableModel = new DefaultTableModel();

    public Transaction() {
        /**
         * Get Database Connection from Main
         */
        transactionConnection = Main.getDatabaseConnection();

        /**
         * Creating view
         */
        initFrame("Data Transaksi");
        initTransactionTable();

        /**
         * Creating action
         * @onClicked, @onPressed, etc
         */
        initButtonCari();
    }

    private void initFrame(String name) {
        transactionFrame = new JFrame(name);
        transactionFrame.setContentPane(transactionPanel);
        transactionFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        transactionFrame.pack();

        transactionFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                Main.route(Naming.TEXT_ROUTE_MAIN, transactionFrame);
            }
        });
    }

    public JTable getTransactionTable() {
        return transactionTable;
    }

    public void show() {
        transactionFrame.setVisible(true);
    }

    private void clearTransactionTable() {
        /**
         * Delete all data on table
         */
        transactionTableModel.setRowCount(0);
        getTransactionTable().setModel(transactionTableModel);
    }

    private void initTransactionTable() {
        /**
         * Create empty table with column
         */
        transactionTableModel.addColumn("Id");
        transactionTableModel.addColumn("Total");
        transactionTableModel.addColumn("Bayar");
        transactionTableModel.addColumn("Kembali");
        transactionTableModel.addColumn("Admin Id");
        transactionTableModel.addColumn("Admin Nama");
        transactionTableModel.addColumn("Admin Username");
        transactionTableModel.addColumn("Nama Produk");
        transactionTableModel.addColumn("Harga Produk");
        transactionTableModel.addColumn("Jumlah");

        loadDataTable();
        getTransactionTable().setModel(transactionTableModel);
    }

    private void loadDataTable() {
        clearTransactionTable();
        List<MTransaction> transactions = new RTransaction(transactionConnection).selectAllTransaction();

        for (MTransaction transaction: transactions) {
            MUser user = new RUser(transactionConnection).selectOneUser(transaction.getUser_id());
            List<MTransactionItem> transactionItems = new RTransactionItem(transactionConnection).selectAllTransactionItem();

            for (MTransactionItem transactionItem: transactionItems) {
                transactionTableModel.addRow(new String[] {
                        String.valueOf(transaction.getId()),
                        String.valueOf(transaction.getTotal()),
                        String.valueOf(transaction.getPaying()),
                        String.valueOf(transaction.getPaying_return()),
                        String.valueOf(transaction.getUser_id()),
                        user.getName(),
                        user.getUsername(),
                        transactionItem.getName(),
                        String.valueOf(transactionItem.getPrice()),
                        String.valueOf(transactionItem.getQty())
                });
            }
        }
        getTransactionTable().setModel(transactionTableModel);
    }

    private void initButtonCari() {
        cariButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (!transactionCariTextField.getText().isEmpty()) {
                    String username = transactionCariTextField.getText();
                    MUser user = new RUser(transactionConnection).selectOneUserByUsername(username);
                    if (user != null) {
                        List<MTransaction> transactions = new RTransaction(transactionConnection).selectAllTransactionByUserId(user.getId());
                        if (transactions.size() > 0) {
                            clearTransactionTable();

                            for (MTransaction transaction: transactions) {
                                user = new RUser(transactionConnection).selectOneUser(transaction.getUser_id());
                                List<MTransactionItem> transactionItems = new RTransactionItem(transactionConnection).selectAllTransactionItem();

                                for (MTransactionItem transactionItem: transactionItems) {
                                    transactionTableModel.addRow(new String[] {
                                            String.valueOf(transaction.getId()),
                                            String.valueOf(transaction.getTotal()),
                                            String.valueOf(transaction.getPaying()),
                                            String.valueOf(transaction.getPaying_return()),
                                            String.valueOf(transaction.getUser_id()),
                                            user.getName(),
                                            user.getUsername(),
                                            transactionItem.getName(),
                                            String.valueOf(transactionItem.getPrice()),
                                            String.valueOf(transactionItem.getQty())
                                    });
                                }
                            }

                            getTransactionTable().setModel(transactionTableModel);
                        } else {
                            Helper.showDialog("Username tidak memiliki riwayat");
                        }
                    } else {
                        Helper.showDialog("Username tidak terdaftar");
                    }
                } else {
                    loadDataTable();
                }
            }
        });
    }
}
