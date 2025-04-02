package dao.impl;

import dao.SaleDao;
import db.DatabaseConnection;
import entity.Sale;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SaleDaoImpl implements SaleDao {

    @Override
    public void insert(Sale sale, int id) {
        String sql = "INSERT INTO sale (article, price, quantity, total, idUser) VALUES (?, ?, ?, ?, ?)";
        try{
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, sale.getArticle());
            preparedStatement.setInt(2, sale.getPrice());
            preparedStatement.setInt(3, sale.getQuantity());
            preparedStatement.setInt(4, sale.getTotal());
            preparedStatement.setInt(5, sale.getIdUser());

            preparedStatement.executeUpdate();
            DatabaseConnection.closeConnection();
        }
        catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Erreur BD");
        }
    }

    @Override
    public void update(Sale sale) {
        String sql = "UPDATE sale SET article = ?, price = ?, quantity = ?, total = ? WHERE id = ?";

        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, sale.getArticle());
            preparedStatement.setInt(2, sale.getPrice());
            preparedStatement.setInt(3, sale.getQuantity());
            preparedStatement.setInt(4, sale.getTotal());
            preparedStatement.setInt(5, sale.getIdUser());

            preparedStatement.executeUpdate();

            DatabaseConnection.closeConnection();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur de mise à Jour");
        }

    }

    @Override
    public Sale findById(int id) {
        String sql = "SELECT * from sale WHERE id = ?";
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()) {
                return new Sale(
                        resultSet.getInt("id"),
                        resultSet.getString("article"),
                        resultSet.getInt("price"),
                        resultSet.getInt("quantity"),
                        resultSet.getInt("total"),
                        resultSet.getInt("idUser")
                );
            }
            DatabaseConnection.closeConnection();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur de la recherche");
        }
        return null;
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM sale WHERE id = ?";

        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, id);

            statement.executeUpdate();

            DatabaseConnection.closeConnection();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur de la suppression");
        }
    }

    @Override
    public List<Sale> findAll() {
        List<Sale> sales = new ArrayList<>();


        String sql = "SELECT * FROM sale";


        try {
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                sales.add(
                 new Sale(
                        resultSet.getInt("id"),
                        resultSet.getString("article"),
                        resultSet.getInt("price"),
                        resultSet.getInt("quantity"),
                        resultSet.getInt("total"),
                        resultSet.getInt("idUser")
                )
                );
            }

            DatabaseConnection.closeConnection();

        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur de récuperation");
        }
        return sales;
    }
}
