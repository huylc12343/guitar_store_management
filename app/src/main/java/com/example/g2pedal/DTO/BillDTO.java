package com.example.g2pedal.DTO;

import java.util.List;

public class BillDTO {
    private String billId;
    private List<String> productIds;
    private double totalPay;
    private String date;

    public BillDTO(String billId, List<String> productIds, double totalPay, String date) {
        this.billId = billId;
        this.productIds = productIds;
        this.totalPay = totalPay;
        this.date = date;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
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