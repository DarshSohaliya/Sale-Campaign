package com.example.Sale.Campaign.Service;


import com.example.Sale.Campaign.Model.Campaign;
import com.example.Sale.Campaign.Model.CampaignDiscount;
import com.example.Sale.Campaign.Model.PriceHistory;
import com.example.Sale.Campaign.Model.Product;
import com.example.Sale.Campaign.Repository.CampaignRepository;
import com.example.Sale.Campaign.Repository.PricehistoryRepository;
import com.example.Sale.Campaign.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class CampaignScheduler {

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PricehistoryRepository pricehistoryRepository;

    @Scheduled(fixedRate = 1000)

    @Transactional
    public void applyCampaignDiscounts(){
        LocalDate today = LocalDate.now();
        List<Campaign> campaigns = campaignRepository.findByStartDate(today.toString());

        for(Campaign campaign:campaigns){
            for(CampaignDiscount discount:campaign.getCampaignDiscounts()){
                Product product = productRepository.findByProductId(Long.valueOf(discount.getProductId()));

                if(product!=null){
                    PriceHistory priceHistory = new PriceHistory();
                    priceHistory.setProductId(String.valueOf(product.getProductId()));
                    priceHistory.setOldPrice(product.getCurrentPrice());
                    priceHistory.setNewPrice(calculateNewPrice(product.getCurrentPrice(), product.getDiscount()));
                    priceHistory.setChangeDate(today.toString());
                    pricehistoryRepository.save(priceHistory);


                    double newPrice = calculateNewPrice(product.getCurrentPrice() ,product.getDiscount());
                    product.setCurrentPrice(newPrice);
                    product.setDiscount(discount.getDiscount());
                    productRepository.save(product);

                }
            }
        }
    }

//    @Scheduled(cron = "*/10 * * * * *")
@Transactional
    public void restorePriceAfterCampaignEnd(){
        LocalDate tody = LocalDate.now();
        List<Campaign> campaigns = campaignRepository.findByEndDate(tody.toString());

        for(Campaign campaign : campaigns){
            for(CampaignDiscount discount : campaign.getCampaignDiscounts()){
                Product product = productRepository.findByProductId(Long.valueOf(discount.getProductId()));
                if(product != null){
                    PriceHistory lastHistory = pricehistoryRepository.findTopByProductIdOrderByChangeDateDesc(String.valueOf(product.getProductId()));
                    if (lastHistory!=null){
                        product.setCurrentPrice(lastHistory.getOldPrice());
                        productRepository.save(product);
                    }
                }
            }
        }
    }


    private double calculateNewPrice(double currentPrice,double Discount){
        return currentPrice - (currentPrice * Discount / 100);
    }


}
