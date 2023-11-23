package com.example.repository;

import com.example.model.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {

    CartDetail findByCart_IdAndProduct_Id(Long cartId, Long productId);

    List<CartDetail> findByCart_Id(Long cartId);
}
