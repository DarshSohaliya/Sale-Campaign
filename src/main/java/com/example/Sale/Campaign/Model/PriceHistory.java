package com.example.Sale.Campaign.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class PriceHistory {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private long historyId;
   private String productId;
   private double oldPrice;

    public double getOldDiscount() {
        return oldDiscount;
    }

    public void setOldDiscount(double oldDiscount) {
        this.oldDiscount = oldDiscount;
    }

    public double getNewDiscount() {
        return newDiscount;
    }

    public void setNewDiscount(double newDiscount) {
        this.newDiscount = newDiscount;
    }

    private double oldDiscount;
   private double newDiscount;

    public long getHistoryId() {
        return historyId;
    }

    public void setHistoryId(long historyId) {
        this.historyId = historyId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public double getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(double newPrice) {
        this.newPrice = newPrice;
    }

    public double getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(double oldPrice) {
        this.oldPrice = oldPrice;
    }

    public String getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(String changeDate) {
        this.changeDate = changeDate;
    }

    private double newPrice;
   private String changeDate;



}
