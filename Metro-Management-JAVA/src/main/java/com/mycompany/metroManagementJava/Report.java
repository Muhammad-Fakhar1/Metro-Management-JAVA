package com.mycompany.metroManagementJava;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Report {
    private double totalSale;
    private double totalPurchase;
    private double monthlyRevenue;

    @JsonCreator
    public Report(
            @JsonProperty("totalSale") double totalSale,
            @JsonProperty("totalPurchase") double totalPurchase,
            @JsonProperty("monthlyRevenue") double monthlyRevenue) {
        this.totalSale = totalSale;
        this.totalPurchase = totalPurchase;
        this.monthlyRevenue = monthlyRevenue;
    }

    public double getTotalSale() {
        return totalSale;
    }

    public void setTotalSale(double totalSale) {
        this.totalSale = totalSale;
    }

    public double getTotalPurchase() {
        return totalPurchase;
    }

    public void setTotalPurchase(double totalPurchase) {
        this.totalPurchase = totalPurchase;
    }

    public double getMonthlyRevenue() {
        return monthlyRevenue;
    }

    public void setMonthlyRevenue(double monthlyRevenue) {
        this.monthlyRevenue = monthlyRevenue;
    }

    @Override
    public String toString() {
        return "Report{" +
                "totalSale=" + totalSale +
                ", totalPurchase=" + totalPurchase +
                ", monthlyRevenue=" + monthlyRevenue +
                '}';
    }
}
