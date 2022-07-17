package com.assignment.Firestore.controller;

import com.assignment.Firestore.Entity.Product;
import com.assignment.Firestore.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productService.fetchProductList();
    }

    @GetMapping("/products/{barcode}")
    public Product fetchProductByBarcode(@PathVariable("barcode") String productBarcode) {
        return productService.fetchProductByBarcode(productBarcode);
    }

    @GetMapping("/products/price")
    public List<Product> getAllProductByValue(@RequestParam(name = "gt") Long greaterThan, @RequestParam(name = "lt") Long lessThan) {
        return productService.fetchProductsBy(greaterThan, lessThan);

    }
}
