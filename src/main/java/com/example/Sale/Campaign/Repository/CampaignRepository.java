package com.example.Sale.Campaign.Repository;

import com.example.Sale.Campaign.Model.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign,Long> {


    List<Campaign> findByStartDate(String startdate);

    List<Campaign> findByEndDate(String endate);
}
