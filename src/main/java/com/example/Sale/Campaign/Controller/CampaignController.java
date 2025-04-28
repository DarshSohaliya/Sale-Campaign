package com.example.Sale.Campaign.Controller;

import com.example.Sale.Campaign.Model.Campaign;
import com.example.Sale.Campaign.Service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class CampaignController {

    @Autowired
    CampaignService campaignService;


    @PostMapping("/add-campaign")
    public ResponseEntity<?> AddCampaign(@RequestBody Campaign campaign){
       return campaignService.AddCampaign(campaign);
    }

}
