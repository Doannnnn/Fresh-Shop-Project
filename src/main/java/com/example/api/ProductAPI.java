package com.example.api;

import com.example.exception.DataInputException;
import com.example.model.Product;
import com.example.model.dto.request.ProductReqDTO;
import com.example.model.dto.response.ProductResDTO;
import com.example.service.product.IProductService;
import com.example.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductAPI {

    @Autowired
    private IProductService productService;

    @Autowired
    private AppUtils appUtils;

    @GetMapping
    public ResponseEntity<?> findAllProduct() {
        List<ProductResDTO> productResDTOList = productService.findAllProductResDTO();

        return new ResponseEntity<>(productResDTOList, HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> findById(@PathVariable Long productId) {

        Product product = productService.findById(productId).orElseThrow(() -> new DataInputException("Product not found"));

        ProductResDTO productResDTO = product.toProductResDTO();

        return new ResponseEntity<>(productResDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ProductReqDTO productReqDTO, BindingResult bindingResult) {
        productReqDTO.validate(productReqDTO, bindingResult);

        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        Product product = productReqDTO.toProduct();
        productService.save(product);

        ProductResDTO productResDTO = product.toProductResDTO();

        return new ResponseEntity<>(productResDTO, HttpStatus.CREATED);
    }

    @PatchMapping ("/{productId}")
    public ResponseEntity<?> update(@PathVariable Long productId, @RequestBody ProductReqDTO productReqDTO, BindingResult bindingResult) {
        productReqDTO.validate(productReqDTO, bindingResult);

        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        Product product = productReqDTO.toProduct();
        product.setId(productId);
        productService.update(product);

        ProductResDTO productResDTO = product.toProductResDTO();

        return new ResponseEntity<>(productResDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> delete(@PathVariable Long productId){

        productService.deleteById(productId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
