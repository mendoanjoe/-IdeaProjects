package com.mendoanjoe.manamart.repostiory;

import com.mendoanjoe.manamart.Helper;
import com.mendoanjoe.manamart.model.MUser;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RUser {
    private static Connection connection;
    private static MUser data = null;
    private static ResultSet resultSet = null;
    private static PreparedStatement statement = null;

    /**
     * Always need to create Object using
     * new RUser(db)
     */
    public RUser(Connection conn) {
        connection = conn;
    }

    public int insertUser(String name, String username, String password, String address, Date dob) {
        String query = "insert into users (name, username, password, address, dob) VALUES (?,?,?,?,?)";
        try {
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, name);
            statement.setString(2, username);
            statement.setString(3, password);
            statement.setString(4, address);
            statement.setDate(5, new java.sql.Date(dob.getTime()));

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

    public boolean updateUser(String name, String username, String password, String address, Date dob, int userId) {
        String query = "update users set name = ?, username = ?, password = ?, address = ?, dob = ? where id = ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, username);
            statement.setString(3, password);
            statement.setString(4, address);
            statement.setDate(5, new java.sql.Date(dob.getTime()));
            statement.setInt(6, userId);
            int rowAffected = statement.executeUpdate();
            if (rowAffected == 1)
                return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            Helper.showDialog("SQL Error: " + ex.getMessage());
        }
        return false;
    }

    public boolean deleteUser(int userId) {
        String query = "delete from users where id = ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, userId);

            int rowAffected = statement.executeUpdate();
            if (rowAffected == 1)
                return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            Helper.showDialog("SQL Error: " + ex.getMessage());
        }
        return false;
    }

    public List<MUser> selectAllUser() {
        List<MUser> dataQuery = new ArrayList<>();
        String query = "select * from users";
        try {
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                MUser data = new MUser(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("address"),
                        resultSet.getDate("dob")
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

    public MUser selectOneUserByUsername(String username) {
        String query = "select * from users where username = ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, username);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                data = new MUser(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("address"),
                        resultSet.getDate("dob")
                );
                return data;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            Helper.showDialog("SQL Error: " + ex.getMessage());
        }
        return null;
    }

    public MUser selectOneUser(int id) {
        String query = "select * from users where id = ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                data = new MUser(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("address"),
                        resultSet.getDate("dob")
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
