package com.example.repository;

import com.example.model.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {

    void deleteAllByCart_Id(Long cart_id);

    CartDetail findByCart_IdAndProduct_Id(Long cartId, Long productId);

    List<CartDetail> findByCart_Id(Long cartId);
}
