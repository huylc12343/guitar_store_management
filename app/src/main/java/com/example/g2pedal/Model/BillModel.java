package com.example.g2pedal.Model;


import java.util.ArrayList;
import java.util.List;
// BillModel là giá trị singleton dùng xuyên suốt project

public class BillModel {
    private static BillModel instance;
    private double pay;
    private List<String> productId;

    private BillModel() {
        pay = 0;
        productId = new ArrayList<>();
    }

    public static synchronized BillModel getInstance() {
        if (instance == null) {
            instance = new BillModel();
        }
        return instance;
    }
    public List<String> getBillItems() {
        return productId;
    }

    public void addToBill(StorageDataModel product){
        productId.add(product.getId());
        double price = Double.parseDouble(product.getPrice());
        pay += price;


    }
    public void removeFromBill(StorageDataModel product,int position){
        productId.remove(product.id);
        double price = Double.parseDouble(product.getPrice());
        pay -= price;
    }
    public double getPay() {
        return pay;
    }
    public void clearBill() {
        pay = 0;
        productId.clear();
    }
}


