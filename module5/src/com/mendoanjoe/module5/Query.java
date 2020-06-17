package com.mendoanjoe.module5;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Query {
    private static List<Data> dataList = new ArrayList<>();
    private static Connection connection;

    public Query(Connection conn) {
        connection = conn;
    }

    void insertMahasiswa(String nama, String alamat, String telepon) {
        String query = "insert into mahasiswa (nama, alamat, telepon) VALUES (?,?,?)";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, nama);
            statement.setString(2, alamat);
            statement.setString(3, telepon);
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    void updateMahasiswa(String nama, String alamat, String telepon, int currentIndex) {
        String query = "update mahasiswa set nama = ?, alamat = ?, telepon = ? where id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, nama);
            statement.setString(2, alamat);
            statement.setString(3, telepon);
            statement.setInt(4, dataList.get(currentIndex).getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    void deleteMahasiswa(int currentIndex) {
        String query = "delete from mahasiswa where id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, dataList.get(currentIndex).getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<Data> selectAllMahasiswa() {
        List<Data> dataQuery = new ArrayList<>();
        String query = "select * from mahasiswa";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Data data = new Data(
                        resultSet.getInt("id"),
                        resultSet.getString("nama"),
                        resultSet.getString("alamat"),
                        resultSet.getString("telepon")
                );
                dataQuery.add(data);
            }

            return dataQuery;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataQuery;
    }
}
