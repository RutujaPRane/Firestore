package com.assignment.Firestore.Service;

import com.assignment.Firestore.Entity.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {


    public List<Product> readFromFile() {
        // create Object Mapper
        ObjectMapper mapper = new ObjectMapper();

// read JSON file and map/convert to java POJO
        try {
            Product[] products = mapper.readValue(new File("src/main/resources/Items-Sample-Data.json"), Product[].class);
            return Arrays.stream(products).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

//    public static void main(String[] args) {
//        System.out.println(readFromFile());
//    }


    public List<Product> fetchProductList() {
        return readFromFile();
    }

    public Product fetchProductByBarcode(String productBarcode) {
        List<Product> products = readFromFile();
        return products.stream()
                .filter(product -> product.getBarcode().equals(productBarcode))
                .findAny()
                .orElse(null);
    }

    public List<Product> fetchProductsBy(Long greaterThan, Long lessThan) {
        List<Product> products = readFromFile();
        return products.stream()
                .filter(product -> Long.parseLong(product.getPrice1()) > greaterThan && Long.parseLong(product.getPrice1()) < lessThan)
                .collect(Collectors.toList());
    }
}
