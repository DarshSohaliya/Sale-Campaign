package com.example.Sale.Campaign.Controller;

import com.example.Sale.Campaign.Model.Product;
import com.example.Sale.Campaign.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/add-product")
    public ResponseEntity<?> AddProduct(@RequestBody Product product){
        return productService.AddProduct(product);
    }

    @GetMapping("/get-product")
    public ResponseEntity<?> GetProduct(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size){
        return productService.GetProducts(page, size);
    }
}
