package com.example.orderfood.allModel;

public class RevenueModel {
    private String idRevenue;
    private String maDon;
    private float priceSum;
    private String dateOrder;

    public RevenueModel() {
    }

    public RevenueModel(String idRevenue, String maDon, float priceSum, String dateOrder) {
        this.idRevenue = idRevenue;
        this.maDon = maDon;
        this.priceSum = priceSum;
        this.dateOrder = dateOrder;
    }

    public String getIdRevenue() {
        return idRevenue;
    }

    public void setIdRevenue(String idRevenue) {
        this.idRevenue = idRevenue;
    }

    public String getMaDon() {
        return maDon;
    }

    public void setMaDon(String maDon) {
        this.maDon = maDon;
    }

    public float getPriceSum() {
        return priceSum;
    }

    public void setPriceSum(float priceSum) {
        this.priceSum = priceSum;
    }

    public String getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(String dateOrder) {
        this.dateOrder = dateOrder;
    }
}
