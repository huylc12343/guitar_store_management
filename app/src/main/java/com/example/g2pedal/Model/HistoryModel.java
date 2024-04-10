package com.example.g2pedal.Model;


import java.util.List;

public class HistoryModel {
    private String historyId;
    private List<String> productIds;
    private double totalPay;
    private String date;

    public HistoryModel() {
    }

    public HistoryModel(String historyId, List<String> productIds, double totalPay, String date) {
        this.historyId = historyId;
        this.productIds = productIds;
        this.totalPay = totalPay;
        this.date = date;
    }

    public String getHistoryId() {
        return historyId;
    }

    public void setHistoryId(String historyId) {
        this.historyId = historyId;
    }

    public List<String> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<String> productIds) {
        this.productIds = productIds;
    }

    public double getTotalPay() {
        return totalPay;
    }

    public void setTotalPay(double totalPay) {
        this.totalPay = totalPay;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}