package com.example.g2pedal.Model;

import android.net.Uri;

public class StorageDataModel {
    String id;
    private Uri dataIMG;
    private String tittle;
    private String price;
    private String status;

    public StorageDataModel(String id, String tittle, String price, String status) {
        this.id = id;
        this.tittle = tittle;
        this.price = price;
        this.status = status;
    }

    public StorageDataModel(String id, Uri dataIMG, String tittle, String price, String status) {
        this.id = id;
        this.dataIMG = dataIMG;
        this.tittle = tittle;
        this.price = price;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Uri getDataIMG() {
        return dataIMG;
    }

    public void setDataIMG(Uri dataIMG) {
        this.dataIMG = dataIMG;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
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
}
