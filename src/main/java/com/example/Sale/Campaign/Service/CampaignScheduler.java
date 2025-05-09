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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CampaignScheduler {

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PricehistoryRepository pricehistoryRepository;

//    @Scheduled(fixedRate = 600000)
    @Transactional
    public void applyCampaignDiscounts(){
        LocalDate today = LocalDate.now();
        List<Campaign> campaigns = campaignRepository.findByStartDate(today.toString());

        Map<Long,Double> productIdToMaxDiscount = new HashMap<>();

        for(Campaign campaign:campaigns){
            for(CampaignDiscount discount:campaign.getCampaignDiscounts()){
                Product product = productRepository.findByProductId(Long.valueOf(discount.getProductId()));


                Long productId = Long.valueOf(discount.getProductId());
                double discountPercentage = discount.getDiscount();

                if(!productIdToMaxDiscount.containsKey(productId) || discountPercentage > productIdToMaxDiscount.get(productId)){
                     productIdToMaxDiscount.put(productId,discountPercentage);
                }
//                if(product!=null){
//                    PriceHistory priceHistory = new PriceHistory();
//                    priceHistory.setProductId(String.valueOf(product.getProductId()));
//                    priceHistory.setOldPrice(product.getCurrentPrice());
//                    priceHistory.setNewPrice(calculateNewPrice(product.getProductMRP(), product.getDiscount()));
//                    priceHistory.setChangeDate(today.toString());
//                    priceHistory.setNewDiscount(discount.getDiscount());
//                    priceHistory.setOldDiscount(product.getDiscount());
//                    pricehistoryRepository.save(priceHistory);
//
//
//                    double newPrice = calculateNewPrice(product.getProductMRP() ,discount.getDiscount());
//                    product.setCurrentPrice(newPrice);
//                    product.setDiscount(discount.getDiscount());
//                    productRepository.save(product);
//
//                }
            }
        }
        for(Map.Entry<Long,Double> entry : productIdToMaxDiscount.entrySet()){
            Long productId = entry.getKey();
            double maxDiscount = entry.getValue();

            Product product = productRepository.findByProductId(productId);
            if(product != null){
                PriceHistory priceHistory = new PriceHistory();
                    priceHistory.setProductId(String.valueOf(product.getProductId()));
                    priceHistory.setOldPrice(product.getCurrentPrice());
                    priceHistory.setNewPrice(calculateNewPrice(product.getProductMRP(), product.getDiscount()));
                    priceHistory.setChangeDate(today.toString());
                    priceHistory.setNewDiscount(maxDiscount);
                    priceHistory.setOldDiscount(product.getDiscount());
                    pricehistoryRepository.save(priceHistory);

                double newPrice = calculateNewPrice(product.getProductMRP() ,maxDiscount);
                    product.setCurrentPrice(newPrice);
                    product.setDiscount(maxDiscount);
                    productRepository.save(product);
            }
        }
    }

//    @Scheduled(cron = "*/10 * * * * *")
    @Scheduled(fixedRate = 600000)


@Transactional
    public void restorePriceAfterCampaignEnd(){
        LocalDate tody = LocalDate.now();
        List<Campaign> campaigns = campaignRepository.findByEndDate(tody.toString());
      System.out.println("###111");
      System.out.println(campaigns);
        for(Campaign campaign : campaigns){
            for(CampaignDiscount discount : campaign.getCampaignDiscounts()){
                System.out.println("###222");
                Product product = productRepository.findByProductId(Long.valueOf(discount.getProductId()));

                if(product != null){
                    List<Campaign> activeCampaigns = campaignRepository.findByProductIdAndEndDateAfter(product.getProductId(),tody.toString());

                    if(activeCampaigns != null && !activeCampaigns.isEmpty()){
                        double maxDiscount = 0;
                        for(Campaign activeCampaign : activeCampaigns){
                            for(CampaignDiscount activeDiscont : activeCampaign.getCampaignDiscounts()){
                                if(Long.valueOf(activeDiscont.getProductId()).equals(product.getProductId())){
                                    if(activeDiscont.getDiscount() > maxDiscount){
                                        maxDiscount = activeDiscont.getDiscount();
                                    }
                                }
                            }
                        }
                        double newPrice = calculateNewPrice(product.getProductMRP(),maxDiscount);
                        product.setCurrentPrice(newPrice);
                        product.setDiscount(maxDiscount);
                        productRepository.save(product);
                    }else{
                        PriceHistory lastHistory = pricehistoryRepository.findTopByProductIdOrderByChangeDateDesc(String.valueOf(product.getProductId()));
                        if(lastHistory !=null){
                            product.setCurrentPrice(lastHistory.getOldPrice());
                            product.setDiscount(lastHistory.getOldDiscount());
                            productRepository.save(product);
                        }
                    }

//                    PriceHistory lastHistory = pricehistoryRepository.findTopByProductIdOrderByChangeDateDesc(String.valueOf(product.getProductId()));
//                    System.out.println("###333");
//                    if (lastHistory!=null){
//                        product.setCurrentPrice(lastHistory.getOldPrice());
//                        product.setDiscount(lastHistory.getOldDiscount());
//                        productRepository.save(product);
//                    }
                }
            }
        }
    }


    private double calculateNewPrice(double currentPrice,double Discount){
        return currentPrice - (currentPrice * Discount / 100);
    }


}
