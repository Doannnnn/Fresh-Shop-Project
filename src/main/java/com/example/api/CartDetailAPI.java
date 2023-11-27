package com.example.api;

import com.example.exception.DataInputException;
import com.example.model.CartDetail;
import com.example.model.Product;
import com.example.model.dto.request.CartDetailReqDTO;
import com.example.model.dto.request.ProductReqDTO;
import com.example.model.dto.response.CartDetailResDTO;
import com.example.model.dto.response.ProductResDTO;
import com.example.service.cartDetail.ICartDetailService;
import com.example.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cart-detail")
public class CartDetailAPI {

    @Autowired
    private ICartDetailService cartDetailService;

    @Autowired
    private AppUtils appUtils;

    @GetMapping("/{username}")
    public ResponseEntity<?> findAllCartDetailByUser(@PathVariable String username) {
        List<CartDetailResDTO> cartDetailResDTOS = cartDetailService.findAllCartDetailByUser(username);

        return new ResponseEntity<>(cartDetailResDTOS, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CartDetailReqDTO cartDetailReqDTO, BindingResult bindingResult) {
        cartDetailReqDTO.validate(cartDetailReqDTO, bindingResult);

        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        cartDetailReqDTO.setQuantity("1");
        CartDetail cartDetail = cartDetailService.create(cartDetailReqDTO);

        CartDetailResDTO cartDetailResDTO = cartDetail.toCardDetailResDTO();

        return new ResponseEntity<>(cartDetailResDTO, HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity<?> createQuantity(@RequestBody CartDetailReqDTO cartDetailReqDTO, BindingResult bindingResult) {
        cartDetailReqDTO.validate(cartDetailReqDTO, bindingResult);

        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        CartDetail cartDetail = cartDetailService.create(cartDetailReqDTO);

        CartDetailResDTO cartDetailResDTO = cartDetail.toCardDetailResDTO();

        return new ResponseEntity<>(cartDetailResDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/{cartDetailId}")
    public ResponseEntity<?> deleted(@PathVariable Long cartDetailId) {
        Optional<CartDetail> existingCartDetail = cartDetailService.findById(cartDetailId);

        if (existingCartDetail.isPresent()) {
            cartDetailService.deleteById(cartDetailId);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>("CartDetail not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{cartDetailId}")
    public ResponseEntity<?> changeQuantity(@PathVariable Long cartDetailId, @RequestBody String newQuantity) {

        Optional<CartDetail> existingCartDetail = cartDetailService.findById(cartDetailId);

        if (existingCartDetail.isPresent()) {
            cartDetailService.changeQuantity(cartDetailId, Long.valueOf(newQuantity));
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>("CartDetail not found", HttpStatus.NOT_FOUND);
        }
    }

}
