package com.mendoanjoe.manamart.repostiory;

import com.mendoanjoe.manamart.Helper;
import com.mendoanjoe.manamart.model.MTransactionItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RTransactionItem {
    private static Connection connection;
    private static MTransactionItem data = null;
    private static ResultSet resultSet = null;
    private static PreparedStatement statement = null;

    /**
     * Always need to create Object using
     * new RTransactionItem(db)
     */
    public RTransactionItem(Connection conn) {
        connection = conn;
    }

    public int insertTransactionItem(String name, int price, int qty, int transaction_id, int product_id) {
        String query = "insert into transaction_items (name, price, qty, transaction_id, product_id) VALUES (?,?,?,?,?)";
        try {
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, name);
            statement.setInt(2, price);
            statement.setInt(3, qty);
            statement.setInt(4, transaction_id);
            statement.setInt(5, product_id);

            int rowAffected = statement.executeUpdate();
            if (rowAffected == 1) {
                resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            Helper.showDialog("SQL Error: " + ex.getMessage());
        }
        return -1;
    }

    public boolean updateTransactionItem(String name, int price, int qty, int transaction_id, int product_id, int transactionItemId) {
        String query = "update transaction_items set name = ?, price = ?, qty = ?, transaction_id = ?, product_id = ? where id = ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setInt(2, price);
            statement.setInt(3, qty);
            statement.setInt(4, transaction_id);
            statement.setInt(5, product_id);
            statement.setInt(6, transactionItemId);

            int rowAffected = statement.executeUpdate();
            if (rowAffected == 1)
                return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            Helper.showDialog("SQL Error: " + ex.getMessage());
        }
        return false;
    }

    public boolean deleteTransactionItem(int transactionItemId) {
        String query = "delete from transaction_items where id = ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, transactionItemId);

            int rowAffected = statement.executeUpdate();
            if (rowAffected == 1)
                return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            Helper.showDialog("SQL Error: " + ex.getMessage());
        }
        return false;
    }

    public List<MTransactionItem> selectAllTransactionItem() {
        List<MTransactionItem> dataQuery = new ArrayList<>();
        String query = "select * from transaction_items";
        try {
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                MTransactionItem data = new MTransactionItem(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("price"),
                        resultSet.getInt("qty"),
                        resultSet.getInt("transaction_id"),
                        resultSet.getInt("product_id")
                );
                dataQuery.add(data);
            }

            return dataQuery;
        } catch (SQLException ex) {
            ex.printStackTrace();
            Helper.showDialog("SQL Error: " + ex.getMessage());
        }
        return dataQuery;
    }

    public MTransactionItem selectOneTransactionItem(int transactionItemId) {
        String query = "select * from transaction_items where id = ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, transactionItemId);

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                data = new MTransactionItem(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("price"),
                        resultSet.getInt("qty"),
                        resultSet.getInt("transaction_id"),
                        resultSet.getInt("product_id")
                );
                return data;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            Helper.showDialog("SQL Error: " + ex.getMessage());
        }
        return null;
    }
}
