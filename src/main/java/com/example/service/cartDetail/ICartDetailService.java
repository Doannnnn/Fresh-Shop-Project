package com.example.service.cartDetail;

import com.example.model.CartDetail;
import com.example.model.dto.request.CartDetailReqDTO;
import com.example.model.dto.response.CartDetailResDTO;
import com.example.service.IGeneralService;

import java.util.List;

public interface ICartDetailService extends IGeneralService <CartDetail, Long> {

    List<CartDetailResDTO> findAllCartDetailByUser(String username);

    CartDetail create(CartDetailReqDTO cartDetailReqDTO);
}
