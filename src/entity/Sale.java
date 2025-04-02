package entity;

import java.util.List;

public class Sale {
    private int id;
    private String article;
    private int price;
    private int quantity;
    private int total;
    private int idUser;

    public Sale(int id, String article, int price, int quantity, int total, int idUser) {
        this.id = id;
        this.article = article;
        this.price = price;
        this.quantity = quantity;
        this.total = total;
        this.idUser = idUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
}
