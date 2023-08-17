package com.example.crudapp.controller;

import com.example.crudapp.model.Product;
import com.example.crudapp.model.ProductWrapper;
import com.example.crudapp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("http://localhost:5173")
@RequestMapping("/api")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @GetMapping("/products")
    public ResponseEntity<ProductWrapper> getAllProducts(@RequestParam(required = false) String title) {
        List<Product> productList = new ArrayList<Product>();
        productRepository.findAll().forEach(productList::add);

        ProductWrapper productWrapper = new ProductWrapper(productList);
        return new ResponseEntity<>(productWrapper, HttpStatus.OK);
    }

    @PostMapping("/products")
    public ResponseEntity<Product> addProduct(@RequestBody Product newproduct) {
        Product tempProduct = productRepository.save(new Product(newproduct.getTitle(),
                newproduct.getDescription(),
                newproduct.getImages(),
                newproduct.getPrice()
        ));
        return new ResponseEntity<>(tempProduct, HttpStatus.CREATED);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable("id") long id) {
        productRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PutMapping("/products")
    public ResponseEntity<Product> updateProduct(@RequestBody Product newproduct) {
        long id = newproduct.getId();
        productRepository.save(new Product(id, newproduct.getTitle(),
                newproduct.getDescription(),
                newproduct.getImages(),
                newproduct.getPrice()
        ));

        return new ResponseEntity<>(productRepository.getReferenceById(id), HttpStatus.OK);

    }
}
