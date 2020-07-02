package com.mendoanjoe.manamart.repostiory;

import com.mendoanjoe.manamart.Helper;
import com.mendoanjoe.manamart.model.MTransaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RTransaction {
    private static Connection connection;
    private static MTransaction data = null;
    private static ResultSet resultSet = null;
    private static PreparedStatement statement = null;

    /**
     * Always need to create Object using
     * new RTransaction(db)
     */
    public RTransaction(Connection conn) {
        connection = conn;
    }

    public int insertTransaction(int total, int paying, int paying_return, int user_id) {
        String query = "insert into transactions (total, paying, paying_return, user_id) VALUES (?,?,?,?)";
        try {
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, total);
            statement.setInt(2, paying);
            statement.setInt(3, paying_return);
            statement.setInt(4, user_id);

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

    public boolean updateTransaction(int total, int paying, int paying_return, int user_id, int transactionId) {
        String query = "update transactions set total = ?, paying = ?, paying_return = ?, user_id = ? where id = ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, total);
            statement.setInt(2, paying);
            statement.setInt(3, paying_return);
            statement.setInt(4, user_id);
            statement.setInt(5, transactionId);

            int rowAffected = statement.executeUpdate();
            if (rowAffected == 1)
                return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            Helper.showDialog("SQL Error: " + ex.getMessage());
        }
        return false;
    }

    public boolean deleteTransaction(int transactionId) {
        String query = "delete from trasnactions where id = ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, transactionId);

            int rowAffected = statement.executeUpdate();
            if (rowAffected == 1)
                return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            Helper.showDialog("SQL Error: " + ex.getMessage());
        }
        return false;
    }

    public List<MTransaction> selectAllTransaction() {
        List<MTransaction> dataQuery = new ArrayList<>();
        String query = "select * from transactions";
        try {
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                MTransaction data = new MTransaction(
                        resultSet.getInt("id"),
                        resultSet.getInt("total"),
                        resultSet.getInt("paying"),
                        resultSet.getInt("paying_return"),
                        resultSet.getInt("user_id")
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

    public List<MTransaction> selectAllTransactionByUserId(int userId) {
        List<MTransaction> dataQuery = new ArrayList<>();
        String query = "select * from transactions where user_id = ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                MTransaction data = new MTransaction(
                        resultSet.getInt("id"),
                        resultSet.getInt("total"),
                        resultSet.getInt("paying"),
                        resultSet.getInt("paying_return"),
                        resultSet.getInt("user_id")
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

    public MTransaction selectOneTransaction(int transactionId) {
        String query = "select * from transactions where id = ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, transactionId);

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                data = new MTransaction(
                        resultSet.getInt("id"),
                        resultSet.getInt("total"),
                        resultSet.getInt("paying"),
                        resultSet.getInt("paying_return"),
                        resultSet.getInt("user_id")
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
