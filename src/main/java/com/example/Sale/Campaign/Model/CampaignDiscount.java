package com.example.Sale.Campaign.Model;

import jakarta.persistence.*;

@Entity
public class CampaignDiscount {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Add this
    private Long id;

    private String productId;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    private double discount;
}
