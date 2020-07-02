package com.mendoanjoe.manamart.repostiory;

import com.mendoanjoe.manamart.Helper;
import com.mendoanjoe.manamart.model.MProduct;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RProduct {
    private static Connection connection;
    private static MProduct data = null;
    private static ResultSet resultSet = null;
    private static PreparedStatement statement = null;

    /**
     * Always need to create Object using
     * new RProduct(db)
     */
    public RProduct(Connection conn) {
        connection = conn;
    }

    public int insertProduct(String code, String name, int price) {
        String query = "insert into products (code, name, price) VALUES (?,?,?)";
        try {
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, code);
            statement.setString(2, name);
            statement.setInt(3, price);

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

    public boolean updateProduct(String code, String name, int price, int productId) {
        String query = "update products set code = ?, name = ?, price = ? where id = ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, code);
            statement.setString(2, name);
            statement.setInt(3, price);
            statement.setInt(4, productId);

            int rowAffected = statement.executeUpdate();
            if (rowAffected == 1)
                return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            Helper.showDialog("SQL Error: " + ex.getMessage());
        }
        return false;
    }

    public boolean deleteProduct(int productId) {
        String query = "delete from products where id = ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, productId);

            int rowAffected = statement.executeUpdate();
            if (rowAffected == 1)
                return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            Helper.showDialog("SQL Error: " + ex.getMessage());
        }
        return false;
    }

    public List<MProduct> selectAllProduct() {
        List<MProduct> dataQuery = new ArrayList<>();
        String query = "select * from products";
        try {
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                MProduct data = new MProduct(
                        resultSet.getInt("id"),
                        resultSet.getString("code"),
                        resultSet.getString("name"),
                        resultSet.getInt("price")
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

    public MProduct selectOneProductByCode(String code) {
        String query = "select * from products where code = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, code);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                data = new MProduct(
                        resultSet.getInt("id"),
                        resultSet.getString("code"),
                        resultSet.getString("name"),
                        resultSet.getInt("price")
                );
                return data;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            Helper.showDialog("SQL Error: " + ex.getMessage());
        }
        return null;
    }

    public MProduct selectOneProduct(int productId) {
        String query = "select * from products where id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, productId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                data = new MProduct(
                        resultSet.getInt("id"),
                        resultSet.getString("code"),
                        resultSet.getString("name"),
                        resultSet.getInt("price")
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
