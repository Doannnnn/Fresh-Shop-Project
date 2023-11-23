package com.example.service.cart;

import com.example.model.Cart;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartService implements ICartService {
    @Override
    public List<Cart> findAll() {
        return null;
    }

    @Override
    public Optional<Cart> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void save(Cart cart) {

    }

    @Override
    public void deleteById(Long id) {

    }
}
