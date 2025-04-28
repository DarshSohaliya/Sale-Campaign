package com.example.Sale.Campaign.Repository;

import com.example.Sale.Campaign.Model.PriceHistory;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PricehistoryRepository extends JpaRepository<PriceHistory,Long> {
    PriceHistory findTopByProductIdOrderByChangeDateDesc(String s);

}
