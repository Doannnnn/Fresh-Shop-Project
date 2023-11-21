package com.example.service.product;

import com.example.model.Product;
import com.example.model.dto.response.ProductResDTO;
import com.example.service.IGeneralService;

import java.util.List;
import java.util.Optional;

public interface IProductService extends IGeneralService<Product, Long> {

    List<ProductResDTO> findAllProductResDTO();

    void update(Product product);

}
