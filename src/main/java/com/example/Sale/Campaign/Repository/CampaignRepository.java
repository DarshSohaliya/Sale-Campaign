package com.example.Sale.Campaign.Repository;

import com.example.Sale.Campaign.Model.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign,Long> {


    List<Campaign> findByStartDate(String startdate);

    List<Campaign> findByEndDate(String endate);

    @Query("SELECT c FROM Campaign c JOIN c.campaignDiscounts cd WHERE cd.productId = :productId AND c.endDate > :today")
    List<Campaign> findByProductIdAndEndDateAfter(@Param("productId") Long productId,@Param("today") String string);
}
