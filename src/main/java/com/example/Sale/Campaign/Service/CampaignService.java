package com.example.Sale.Campaign.Service;

import com.example.Sale.Campaign.Model.Campaign;
import com.example.Sale.Campaign.Model.CampaignDiscount;
import com.example.Sale.Campaign.Model.PriceHistory;
import com.example.Sale.Campaign.Model.Product;
import com.example.Sale.Campaign.Repository.CampaignRepository;
import com.example.Sale.Campaign.Repository.PricehistoryRepository;
import com.example.Sale.Campaign.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CampaignService {

    @Autowired
    CampaignRepository campaignRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    PricehistoryRepository pricehistoryRepository;

    public ResponseEntity<?> AddCampaign(Campaign campaign) {
        System.out.println("###--11");

      try {
          campaignRepository.save(campaign);
//          System.out.println("###000");
//
//          List<CampaignDiscount> campaignDiscounts = campaign.getCampaignDiscounts();
//          for(CampaignDiscount campaignDiscount: campaignDiscounts){
//             String productId = campaignDiscount.getProductId();
//            Product product= productRepository.findById(Long.valueOf(productId)).orElse(null);
//              System.out.println("###111");
//
//            if(product!=null){
//                double newPrice = product.getCurrentPrice() * (1 - campaignDiscount.getDiscount() / 100);
//                product.setCurrentPrice(newPrice);
//                productRepository.save(product);
//
//                System.out.println("###222");
//
//                PriceHistory priceHistory =new PriceHistory();
//                priceHistory.setNewPrice(newPrice);
//                priceHistory.setOldPrice(product.getCurrentPrice());
//                priceHistory.setProductId(productId);
//                priceHistory.setChangeDate(LocalDate.now().toString());
//                pricehistoryRepository.save(priceHistory);
//                System.out.println("###333");
//            }
//          }
//          System.out.println("###444");

            return new ResponseEntity<>("Campaign created SuccessFully", HttpStatus.OK);
      } catch (Exception e) {
          System.out.println("### Error: " + e.getMessage());
         return new ResponseEntity<>("Campaign is not created ",HttpStatus.BAD_REQUEST);
      }
    }
}
