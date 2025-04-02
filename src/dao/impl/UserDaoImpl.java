package dao.impl;

import dao.UserDao;
import db.DatabaseConnection;
import entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDaoImpl implements UserDao {
    @Override
    public User login(String email, String password) {
        String sql = "SELECT * from user WHERE email = ? AND password = ?";
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, email);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            User result;
            if(resultSet.next()) {
                result =  new User(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getString("password")
                );
            } else {
                result =  new User(0,"", "","");
            }
            DatabaseConnection.closeConnection();
            return result;

        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur de la recherche");
        }
    }
}
