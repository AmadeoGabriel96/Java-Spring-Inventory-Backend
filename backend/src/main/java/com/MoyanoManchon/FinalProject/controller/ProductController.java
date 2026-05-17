package com.MoyanoManchon.FinalProject.controller;

import com.MoyanoManchon.FinalProject.model.Product;
import com.MoyanoManchon.FinalProject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping(path = "api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping(path = "/")
    public ResponseEntity<Product> create(@RequestBody Product product) throws Exception {
        return new ResponseEntity<>(this.productService.create(product), HttpStatus.OK);
    }

    @PutMapping (path = "/{id}")
    public ResponseEntity<Product> update(@RequestBody Product product, @PathVariable Long id) throws Exception {
        return new ResponseEntity<>(this.productService.update(product, id), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id) throws Exception {
        return new ResponseEntity<>(this.productService.findById(id), HttpStatus.OK);
    }

    @GetMapping(path = "/")
    public ResponseEntity<List<Product>> findByAll(){
        return new ResponseEntity<>(this.productService.findByAll(), HttpStatus.OK);
    }

}

