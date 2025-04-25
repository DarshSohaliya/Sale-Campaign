package com.example.Sale.Campaign.Service;

import com.example.Sale.Campaign.DTO.ProductResponseDTO;
import com.example.Sale.Campaign.Model.Product;
import com.example.Sale.Campaign.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public ResponseEntity<?> AddProduct(Product product){
        try {
            Product savedProduct = productRepository.save(product);
            return new ResponseEntity<>(savedProduct,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Product is Not Added",HttpStatus.BAD_REQUEST);

        }
    }

    public ResponseEntity<?> GetProducts(int page,int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
             Page<Product> productPage = productRepository.findAll(pageable);

             List<Product> products = new ArrayList<>();

             for(Product product : productPage.getContent()){
                 Product product1 = new Product();
                 product1.setProductId(product.getProductId());
                 product1.setProductName(product.getProductName());
                 product1.setProductMRP(product.getProductMRP());
                 product1.setCurrentPrice(product.getCurrentPrice());
                 product1.setDiscount(product.getDiscount());
                 product1.setQuantity(product.getQuantity());

                 products.add(product1);

             }

            Map<String, Object> response = new HashMap<>();
             response.put("products",products);
            response.put("page",page);
            response.put("pageSize",size);
            response.put("totalPages",productPage.getTotalPages());



             return new ResponseEntity<>(response,HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>("Product Not Fetched",HttpStatus.BAD_REQUEST);
        }
    }
}
