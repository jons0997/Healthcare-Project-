package com.example.demo.model;

import com.example.demo.entity.Product;

public class ProductInfo {
    private String code;
    private String name;
    private double price;
    private String category;

    public ProductInfo() {
    }

    public ProductInfo(Product product) {
        this.code = product.getCode();
        this.name = product.getName();
        this.price = product.getPrice();
        this.category = product.getCategory();
    }

    public ProductInfo(String code, String name, double price) {
    	this.code=code;
    	this.name=name;
    	this.price=price;
    }
    // Using in JPA/Hibernate query
    public ProductInfo(String code, String name, double price, String category) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    public String getCategory() {
    	return category;
    }
    
    public void setCategory(String category) {
    	this.category = category;
    }

}