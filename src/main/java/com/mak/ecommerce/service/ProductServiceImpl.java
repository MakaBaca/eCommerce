package com.mak.ecommerce.service;

import com.mak.ecommerce.exception.ProductNotFoundException;
import com.mak.ecommerce.models.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("productService")
public class ProductServiceImpl implements ProductService {

    public List<Product> getAllProducts(){
        return null;
    }

    @Override
    public Product getProductById(Long id) {
        Product p = new Product();
        p.setTitle("Iphone 15");
        return p;
    }

    @Override
    public Product deleteProduct(Long id) throws ProductNotFoundException {
        return null;
    }

    @Override
    public Product addProduct(Product product) {
        return null;
    }

    @Override
    public Product updateProduct(Product product, Long id) throws ProductNotFoundException {
        return null;
    }

}
