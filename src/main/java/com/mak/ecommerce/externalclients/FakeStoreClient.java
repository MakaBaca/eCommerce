package com.mak.ecommerce.externalclients;

import com.mak.ecommerce.dtos.FakeStoreProductDto;
import com.mak.ecommerce.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

@Component
public class FakeStoreClient {

    private final RestTemplateBuilder restTemplateBuilder;
    private final String fakeUrl;

    @Autowired
    public FakeStoreClient(RestTemplateBuilder builder, @Value("${fakestore.base.url}") String url) {
        this.restTemplateBuilder = builder;
        this.fakeUrl = url;
    }

    public FakeStoreProductDto[] getAllProducts() {
        RestTemplate client = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto[]> responseEntity = client.getForEntity(fakeUrl, FakeStoreProductDto[].class);
        return responseEntity.getBody();
    }

    public FakeStoreProductDto getProductById(Long id) throws ProductNotFoundException {
        RestTemplate client = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> responseEntity = client.getForEntity(fakeUrl + "/" + id, FakeStoreProductDto.class);
        if (responseEntity.getBody() == null) {
            throw new ProductNotFoundException("No product found for id: " + id);
        }
        return responseEntity.getBody();
    }

    public FakeStoreProductDto deleteProductById(Long id) throws ProductNotFoundException {
        RestTemplate client = restTemplateBuilder.build();
        //RequestCallback callback = client.acceptHeaderRequestCallback(FakeStoreProductDto.class);
        ResponseExtractor<ResponseEntity<FakeStoreProductDto>> extractor = client.responseEntityExtractor(FakeStoreProductDto.class);
        ResponseEntity<FakeStoreProductDto> responseEntity = client.execute(fakeUrl+"/{id}", HttpMethod.DELETE,null,extractor,id);
        if ( responseEntity == null || responseEntity.getBody() == null) {
            throw new ProductNotFoundException("No product found for id: " + id);
        }
        return responseEntity.getBody();
    }

    public FakeStoreProductDto addProduct(FakeStoreProductDto dto){
        RestTemplate client = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> responseEntity = client.postForEntity(fakeUrl,dto, FakeStoreProductDto.class);
        return responseEntity.getBody();
    }

    public FakeStoreProductDto updateProduct(FakeStoreProductDto dto, Long id) throws ProductNotFoundException {
        RestTemplate client = restTemplateBuilder.build();
        RequestCallback callback = client.httpEntityCallback(dto,FakeStoreProductDto.class);
        ResponseExtractor<ResponseEntity<FakeStoreProductDto>> extractor = client.responseEntityExtractor(FakeStoreProductDto.class);
        ResponseEntity<FakeStoreProductDto> responseEntity = client.execute(fakeUrl+"/{id}", HttpMethod.PUT,callback,extractor,id);
        if ( responseEntity == null || responseEntity.getBody() == null) {
            throw new ProductNotFoundException("No product found for id: " + id);
        }
        return responseEntity.getBody();
    }
}
