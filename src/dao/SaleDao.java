package dao;

import entity.Sale;

import java.util.List;

public interface SaleDao {
    void insert(Sale sale, int id);
    void update(Sale sale);
    Sale findById(int id);
    void deleteById(int id);
    List<Sale> findAll();
}
