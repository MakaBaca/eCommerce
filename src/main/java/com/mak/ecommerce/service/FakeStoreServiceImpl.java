package com.mak.ecommerce.service;

import com.mak.ecommerce.dtos.FakeStoreProductDto;
import com.mak.ecommerce.exception.ProductNotFoundException;
import com.mak.ecommerce.externalclients.FakeStoreClient;
import com.mak.ecommerce.models.Category;
import com.mak.ecommerce.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("Fake")
public class FakeStoreServiceImpl implements ProductService{

    FakeStoreClient client;
    @Autowired
    public FakeStoreServiceImpl(FakeStoreClient client){
        this.client = client;
    }
    @Override
    public List<Product> getAllProducts() {
        List<Product> lst = new ArrayList<>();
        for(FakeStoreProductDto dto : client.getAllProducts()){
            lst.add(getProductFromFakeStoreProductDto(dto));
        }
        return lst;
    }

    @Override
    public Product getProductById(Long id) throws ProductNotFoundException {
        return getProductFromFakeStoreProductDto(client.getProductById(id));
    }

    @Override
    public Product deleteProduct(Long id) throws ProductNotFoundException {
        return getProductFromFakeStoreProductDto(client.deleteProductById(id));
    }

    @Override
    public Product addProduct(Product product) {
        FakeStoreProductDto dto = client.addProduct(getFakeStoreProductDtoFromProduct(product));
        return getProductFromFakeStoreProductDto(dto);
    }

    @Override
    public Product updateProduct(Product product, Long id) throws ProductNotFoundException {
        FakeStoreProductDto dto = client.updateProduct(getFakeStoreProductDtoFromProduct(product), id);
        return getProductFromFakeStoreProductDto(dto);
    }

    private Product getProductFromFakeStoreProductDto(FakeStoreProductDto fakeStoreProductDto){
        Product product = new Product();
        product.setId(fakeStoreProductDto.getId());
        product.setTitle(fakeStoreProductDto.getTitle());
        product.setDescription(fakeStoreProductDto.getDescription());
        Category category = new Category();
        category.setName(fakeStoreProductDto.getCategory());
        product.setCategory(category);
        product.setPrice(fakeStoreProductDto.getPrice());
        return product;
    }

    private FakeStoreProductDto getFakeStoreProductDtoFromProduct(Product product){
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setTitle(product.getTitle());
        fakeStoreProductDto.setDescription(product.getDescription());
        fakeStoreProductDto.setCategory(product.getCategory().getName());
        fakeStoreProductDto.setPrice(product.getPrice());
        return fakeStoreProductDto;
    }


}
