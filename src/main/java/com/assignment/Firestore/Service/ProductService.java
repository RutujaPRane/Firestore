package com.assignment.Firestore.Service;

import com.assignment.Firestore.Entity.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
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

    public synchronized Product addProduct(Product product) {
        List<Product> products = readFromFile();
        products.add(product);
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(Paths.get("src/main/resources/Items-Sample-Data.json").toFile(), products);
            return product;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized Product updateProduct(Long barcode, Product product) {
        List<Product> products = readFromFile();
        for (Product p : products) {
            if (Long.parseLong(p.getBarcode()) == barcode) {
                p.setGroup(product.getGroup());
                p.setCategory(product.getCategory());
                p.setSubCategory(product.getSubCategory());
                p.setItemName(product.getItemName());
                p.setImageUrl(product.getImageUrl());
                p.setType(product.getType());
                p.setPrice1(product.getPrice1());
                p.setQty1(product.getQty1());
                p.setMrp1(product.getMrp1());
                p.setUnit1(product.getUnit1());
                break;
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(Paths.get("src/main/resources/Items-Sample-Data.json").toFile(), products);
            return product;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void deleteProduct(Long barcode) {
        List<Product> products = readFromFile();
        for (Product p : products) {
            if (Long.parseLong(p.getBarcode()) == barcode) {
                products.remove(p);
                break;
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(Paths.get("src/main/resources/Items-Sample-Data.json").toFile(), products);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
