package com.example.service.cartDetail;

import com.example.model.Cart;
import com.example.model.CartDetail;
import com.example.model.Product;
import com.example.model.User;
import com.example.model.dto.request.CartDetailReqDTO;
import com.example.model.dto.response.CartDetailResDTO;
import com.example.repository.CartDetailRepository;
import com.example.repository.CartRepository;
import com.example.repository.ProductRepository;
import com.example.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CartDetailService implements ICartDetailService {

    @Autowired
    private CartDetailRepository cartDetailRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    @Override
    public List<CartDetail> findAll() {
        return cartDetailRepository.findAll();
    }

    @Override
    public List<CartDetailResDTO> findAllCartDetailByUser(String username) {
        User user = userRepository.findByUsername(username);
        Cart cart = cartRepository.findCartByUser_Id(user.getId());

        List<CartDetail> cartDetails = cartDetailRepository.findByCart_Id(cart.getId());

        return cartDetails.stream().map(e ->
                new CartDetailResDTO(e.getId(), e.getQuantity(), e.getTotalAmount(), e.getProduct().toProductResDTO())
        ).collect(Collectors.toList());
    }


    @Override
    public Optional<CartDetail> findById(Long id) {
        return cartDetailRepository.findById(id);
    }

    @Override
    public CartDetail create(CartDetailReqDTO cartDetailReqDTO) {
        User user = userRepository.findByUsername(cartDetailReqDTO.getUsername());
        Cart cart = cartRepository.findCartByUser_Id(user.getId());
        Product product = productRepository.findById(Long.valueOf(cartDetailReqDTO.getProductId())).get();

        CartDetail existingCartDetail = cartDetailRepository.findByCart_IdAndProduct_Id(cart.getId(), product.getId());

        if (existingCartDetail != null) {
            Long newQuantity = existingCartDetail.getQuantity() + Long.valueOf(cartDetailReqDTO.getQuantity());
            BigDecimal newTotalAmount = product.getPrice().multiply(BigDecimal.valueOf(newQuantity));

            existingCartDetail.setQuantity(newQuantity);
            existingCartDetail.setTotalAmount(newTotalAmount);

            cartDetailRepository.save(existingCartDetail);
            return existingCartDetail;
        } else {
            BigDecimal price = product.getPrice();
            Long quantity = Long.valueOf(cartDetailReqDTO.getQuantity());
            BigDecimal totalAmount = price.multiply(BigDecimal.valueOf(quantity));

            CartDetail cartDetail = new CartDetail();
            cartDetail.setQuantity(quantity);
            cartDetail.setTotalAmount(totalAmount);
            cartDetail.setProduct(product);
            cartDetail.setCart(cart);

            cartDetailRepository.save(cartDetail);
            return cartDetail;
        }
    }

    @Override
    public void save(CartDetail cartDetail) {

    }

    @Override
    public void changeQuantity(Long id, Long newQuantity) {
        cartDetailRepository.changeQuantity(id, newQuantity);
    }

    @Override
    public void deleteById(Long id) {
        cartDetailRepository.deleteById(id);
    }
}
