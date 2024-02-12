package com.mak.ecommerce.service;

import com.mak.ecommerce.exception.ProductNotFoundException;
import com.mak.ecommerce.models.Product;

import java.util.List;

public interface ProductService {
    public List<Product> getAllProducts();
    public Product getProductById(Long id) throws ProductNotFoundException;
    public Product deleteProduct(Long id) throws ProductNotFoundException;
    public Product addProduct(Product product);
    public Product updateProduct(Product product, Long id) throws ProductNotFoundException;
}
