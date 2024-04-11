package com.example.g2pedal.Model;



public class SearchModel {
    String id;
    private String productIMG;
    private String category;
    private String tittle;
    private String brand;
    private String color;
    private String quantity;
    private String price;
    private String status;
    private String dayIn;

    public SearchModel() {
    }

    public SearchModel(String id, String productIMG, String category,
                       String tittle, String brand, String color, String quantity,
                       String price, String status, String dayIn) {
        this.id = id;
        this.productIMG = productIMG;
        this.category = category;
        this.tittle = tittle;
        this.brand = brand;
        this.color = color;
        this.quantity = quantity;
        this.price = price;
        this.status = status;
        this.dayIn = dayIn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductIMG() {
        return productIMG;
    }

    public void setProductIMG(String productIMG) {
        this.productIMG = productIMG;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDayIn() {
        return dayIn;
    }

    public void setDayIn(String dayIn) {
        this.dayIn = dayIn;
    }
}
