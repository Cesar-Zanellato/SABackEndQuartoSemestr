package com.back.fortesupermercados.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity @Data @NoArgsConstructor @AllArgsConstructor
public class Product {
    
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    // private Long internalCode;
    // private Long codeProduct;
    private String name;
    private String valueSale; 
    private String valuePurchase;
    private String promotion;
    private String image;
    private String stock;
    private String amount;
    @OneToOne
    private Category category;
    @OneToOne
    private Subcategory subcategory;

}
