package com.mycompany.metroManagementJava;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;

public class Report {

    LocalDate date;
    private double totalSale;
    private double totalPurchase;
    private double profit;

    @JsonCreator
    public Report(
            @JsonProperty("dateCreated") @JsonFormat(pattern = "yyyy-MM-dd") LocalDate date,
            @JsonProperty("totalSale") double totalSale,
            @JsonProperty("totalPurchase") double totalPurchase) {
        this.date=date;
        this.totalSale = totalSale;
        this.totalPurchase = totalPurchase;
        this.profit = totalSale - totalPurchase;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    @Override
    public String toString() {
        return "Report{" + "date=" + date + ", totalSale=" + totalSale + ", totalPurchase=" + totalPurchase + ", profit=" + profit + '}';
    }

 
}
