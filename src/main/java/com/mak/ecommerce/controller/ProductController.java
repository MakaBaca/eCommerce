package com.mak.ecommerce.controller;

import com.mak.ecommerce.exception.ProductNotFoundException;
import com.mak.ecommerce.models.Product;
import com.mak.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;
    public ProductController(@Qualifier("Fake") ProductService service){

        this.productService = service;
    }
    @GetMapping
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) throws ProductNotFoundException {
        return productService.getProductById(id);
    }

    @DeleteMapping("/{id}")
    public Product deleteProductById(@PathVariable Long id) throws ProductNotFoundException {
        return productService.deleteProduct(id);
    }

    @PostMapping
    public Product addProduct(@RequestBody Product product){
        return productService.addProduct(product);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@RequestBody Product product, @PathVariable Long id) throws ProductNotFoundException {
        return productService.updateProduct(product, id);
    }

}
