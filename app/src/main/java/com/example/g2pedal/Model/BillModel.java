package com.example.g2pedal.Model;

import android.net.Uri;

public class BillModel {
    int id;
    private Uri productIMG;
    private String category;
    private String tittle;
    private String brand;
    private String color;
    private String quantity;
    private String price;

    public BillModel(int id, Uri productIMG, String category, String tittle, String brand, String color, String quantity, String price) {
        this.id = id;
        this.productIMG = productIMG;
        this.category = category;
        this.tittle = tittle;
        this.brand = brand;
        this.color = color;
        this.quantity = quantity;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Uri getProductIMG() {
        return productIMG;
    }

    public void setProductIMG(Uri productIMG) {
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
}
